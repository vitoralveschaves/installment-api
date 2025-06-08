package com.application.api.installment.security;

import com.application.api.installment.dto.UserAuthenticationData;
import com.application.api.installment.model.User;
import com.application.api.installment.exception.TokenNotValidException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
public class TokenService {

    @Value("${secret.app.key}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Installment-API")
                    .withSubject(user.getEmail())
                    .withClaim("userId", user.getUuid())
                    .withClaim("name", user.getName())
                    .withClaim("roles", user.getAuthorities()
                            .stream().map(Objects::toString)
                            .toList()
                    )
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while authenticating");
        }
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Installment-API")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new TokenNotValidException("Token inválido ou expirado");
        }
    }

    public UserAuthenticationData getTokenData(String token) {
        var jwt = validateToken(token);
        return UserAuthenticationData.builder()
                .email(jwt.getSubject())
                .userId(jwt.getClaim("userId").asString())
                .name(jwt.getClaim("name").asString())
                .roles(jwt.getClaim("roles").asList(String.class))
                .build();
    }

    public Long getExpiresAt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Installment-API")
                    .build()
                    .verify(token)
                    .getExpiresAtAsInstant().toEpochMilli();
        } catch (JWTVerificationException e) {
            throw new TokenNotValidException("Token inválido ou expirado");
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
