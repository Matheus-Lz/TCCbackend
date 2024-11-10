package com.pap.demo.security;

import com.pap.demo.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Ignorar o filtro JWT para a rota pública /servicos/**
        String requestURI = request.getRequestURI();
        System.out.println("Processando requisição para URI: " + requestURI);
        if (requestURI.startsWith("/servicos")) {
            System.out.println("Rota pública detectada, ignorando o filtro JWT para: " + requestURI);
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("Token JWT extraído com sucesso.");
                System.out.println("Usuário extraído do token: " + username);
            } catch (Exception e) {
                System.out.println("Erro ao extrair o token: " + e.getMessage());
            }
        } else {
            System.out.println("Header Authorization não encontrado ou formato incorreto.");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Iniciando autenticação para o usuário: " + username);
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                System.out.println("Token JWT validado com sucesso para o usuário: " + username);

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                List<String> rolesFromToken = jwtUtil.extractRoles(jwt);
                if (rolesFromToken != null) {
                    System.out.println("Roles extraídas do token: " + rolesFromToken);

                    authorities = rolesFromToken.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }

                System.out.println("Authorities configuradas: " + authorities);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                System.out.println("Token JWT inválido ou expirado para o usuário: " + username);
            }
        } else {
            if (username == null) {
                System.out.println("Nenhum usuário foi extraído do token.");
            } else {
                System.out.println("Contexto de segurança já possui uma autenticação para: " + username);
            }
        }

        chain.doFilter(request, response);
    }
}

