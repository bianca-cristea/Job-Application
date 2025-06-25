package com.cristeabianca.job_application.util;

import com.cristeabianca.job_application.role.Role;
import com.cristeabianca.job_application.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtil {

    private static final String SECRET = "mySecretKeyForJwtTokenThatShouldBeLongEnough";

    public static String generateTestToken(User user) {
        String roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }
}
