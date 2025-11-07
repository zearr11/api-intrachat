package com.api.intrachat.services.impl.other;

import com.api.intrachat.services.interfaces.other.IJwtService;
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

    private static final int DURACION_ACCESS_TOKEN = 86400000; // 1 DIA

    @Override
    public String generarAccessToken(UserDetails userDetails) {
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
                .setExpiration(new Date(System.currentTimeMillis() + DURACION_ACCESS_TOKEN))
                .compact();
    }

    @Override
    public String extraerEmail(String token) {
        return extraerTodosLosClaims(token).getSubject();
    }

    // Si el email coincide y el token no ha expirado
    @Override
    public boolean esUnTokenValido(String token, UserDetails userDetails) {
        String email = extraerEmail(token);
        return (email.equals(userDetails.getUsername()))
                &&
                !elTokenExpiro(token);
    }

    @Override
    public boolean esUnAccessToken(String token) {
        return "access".equals(extraerTodosLosClaims(token).get("token_type", String.class));
    }

    @Override
    public Claims extraerTodosLosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean elTokenExpiro(String token) {
        return extraerTodosLosClaims(token).getExpiration().before(new Date());
    }

    @Override
    public boolean esJwtValido(String token) {
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
    public List<SimpleGrantedAuthority> extraerRoles(String token) {
        String role = extraerTodosLosClaims(token).get("role", String.class);
        return List.of(new SimpleGrantedAuthority(role));
    }

}
