package com.ebidding.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class JwtUtils {
    private static final String SECRET = "123456";

    public static String SignToken(String id, String name, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
            return JWT.create()
                    .withClaim("id", id)
                    .withClaim("name", name)
                    .withClaim("role", role)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            log.error(exception.getMessage());
        }
        return null;
    }

    public static DecodedJWT VerifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm)
                    // reusable verifier instance
                    .build();
            return verifier.verify(token);
        } catch (JWTCreationException exception) {
            log.error(exception.getMessage());
        }
        return null;
    }
}