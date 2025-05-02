package com.kauadev.resume_builder_app.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kauadev.resume_builder_app.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = this.recoverToken(request);

            if (token != null) {
                String usernameSubject = tokenService.validateTokenAndReturnSubject(token);

                UserDetails user = userRepository.findByUsername(usernameSubject);

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    protected String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("AUTHORIZATION");

        if (authHeader == null) {
            return null;
        }

        // onde tiver Bearer, troca por espaço vazio, restando só o token.
        String token = authHeader.replace("Bearer", "");

        return token;
    }

}
