package com.api.intrachat.services.interfaces.others;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public interface IJwtService {

    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String extractEmail(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isAccessToken(String token);
    boolean isRefreshToken(String token);
    Claims extractAllClaims(String token);
    boolean isTokenExpired(String token);
    boolean validateJwtToken(String token);
    List<SimpleGrantedAuthority> extractRoles(String token);
}
