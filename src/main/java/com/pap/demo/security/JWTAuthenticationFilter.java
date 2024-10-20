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

        // Pegar o token do header Authorization
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Verifica se o header contém o token JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);  // Extrai o nome de usuário (email) do token
            } catch (Exception e) {
                // Adicione logs para capturar problemas na extração do token
                System.out.println("Erro ao extrair o token: " + e.getMessage());
            }

            // Logs para acompanhar o processamento do token
            System.out.println("Token JWT extraído: " + jwt);
            System.out.println("Usuário extraído do token: " + username);
        }

        // Verifica se o token foi extraído e se o contexto de segurança não possui autenticação
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Valida o token JWT
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // Cria o objeto de autenticação baseado nas informações do token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Detalhes da requisição são adicionados ao token de autenticação
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Autentica o usuário
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                // Adicione logs caso a validação do token falhe
                System.out.println("Token JWT inválido ou expirado");
            }
        }

        // Continua o filtro
        chain.doFilter(request, response);
    }
}

