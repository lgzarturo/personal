package com.lgzarturo.api.personal.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class JwtGenerator {

    @Value("${api.security.jwt.secret}")
    private String secret;

    @Value("${api.security.jwt.issuer}")
    private String issuer;

    @Value("${api.security.jwt.expiration}")
    private Long expiration;

    public String issueToken(String subject) {
        return issueToken(subject, Map.of());
    }

    public String issueToken(String subject, String ...scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, List<String> scopes) {
        return issueToken(subject, scopes.toArray(new String[0]));
    }

    public String issueToken(String subject, Map<String, Object> claims) {
        log.debug("Issuing token for subject: {}", subject);
        return Jwts.builder()
                .setIssuer(issuer)
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(expiration)))
                .signWith(getSingingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        log.debug("Validating token for subject: {}", username);
        String subject = getSubject(token);
        return subject.equals(username) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }

    private Claims getClaims(String token) {
        log.debug("Getting claims from token: {}", token);
        return Jwts.parserBuilder()
                .setSigningKey(getSingingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSingingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
