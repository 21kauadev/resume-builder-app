package com.kauadev.resume_builder_app.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kauadev.resume_builder_app.domain.user.User;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        // faltando expiresAt
        return JWT.create()
                .withIssuer("resume-app")
                .withSubject(user.getUsername())
                .withExpiresAt(this.genExpirationDateInstant())
                .sign(algorithm);
    }

    public String validateTokenAndReturnSubject(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .withIssuer("resume-app")
                .build()
                .verify(token)
                .getSubject();
    }

    public Instant genExpirationDateInstant() {
        // 2 horas de expiração a partir do momento criado
        // depois converte pra instant
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
