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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Log para verificar a URI da requisição
        System.out.println("Processando requisição para URI: " + request.getRequestURI());

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("Token JWT extraído com sucesso. Usuário: " + username);
            } catch (Exception e) {
                System.out.println("Erro ao extrair o token JWT: " + e.getMessage());
            }
        } else {
            System.out.println("Header Authorization ausente ou com formato incorreto.");
        }

        // Verificar se o usuário foi identificado no token e o contexto ainda está vazio
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Iniciando autenticação para o usuário: " + username);
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Validar o token JWT
            if (jwtUtil.validateToken(jwt, userDetails)) {
                System.out.println("Token JWT validado com sucesso para o usuário: " + username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Definir a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                System.out.println("Token JWT inválido ou expirado para o usuário: " + username);
            }
        } else {
            if (username == null) {
                System.out.println("Nenhum usuário foi extraído do token.");
            } else {
                System.out.println("Contexto de segurança já possui autenticação para: " + username);
            }
        }

        // Continuar o fluxo do filtro
        chain.doFilter(request, response);
    }
}
