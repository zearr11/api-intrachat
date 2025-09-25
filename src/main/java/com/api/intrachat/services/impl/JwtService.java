package com.api.intrachat.services.impl;

import com.api.intrachat.services.interfaces.IJwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class JwtService implements IJwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private static final int ACCESS_TOKEN_DURATION = 86400000; // 1 DIA
    private static final int REFRESH_TOKEN_DURATION = 604800000; // 7 DIAS

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities()
                        .stream()
                        .findFirst()
                        .map(GrantedAuthority::getAuthority)
                        .orElseThrow())
                .claim("token_type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_DURATION))
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(userDetails.getUsername())
                .claim("token_type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_DURATION))
                .compact();
    }

    @Override
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Si el email coincide y el token no ha expirado
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()))
                &&
                !isTokenExpired(token);
    }

    @Override
    public boolean isAccessToken(String token) {
        return "access".equals(extractAllClaims(token).get("token_type", String.class));
    }

    @Override
    public boolean isRefreshToken(String token) {
        return "refresh".equals(extractAllClaims(token).get("token_type", String.class));
    }

    // Retorna un objeto Claims que contiene la información del token
    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Devuelve true si el token ha expirado
    @Override
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    @Override
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    @Override
    public List<SimpleGrantedAuthority> extractRoles(String token) {
        String role = extractAllClaims(token).get("role", String.class);
        return List.of(new SimpleGrantedAuthority(role));
    }

}
