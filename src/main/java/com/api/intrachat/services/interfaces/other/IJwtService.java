package com.api.intrachat.services.interfaces.other;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IJwtService {

    String generarAccessToken(UserDetails userDetails);
    String extraerEmail(String token);
    boolean esUnTokenValido(String token, UserDetails userDetails);
    boolean esUnAccessToken(String token);
    Claims extraerTodosLosClaims(String token);
    boolean elTokenExpiro(String token);
    boolean esJwtValido(String token);
    List<SimpleGrantedAuthority> extraerRoles(String token);

}
