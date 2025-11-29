package com.api.intrachat.controllers.rest;

import com.api.intrachat.services.impl.CustomUserDetailsService;
import com.api.intrachat.services.interfaces.other.IJwtService;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.dto.request.AuthRequest;
import com.api.intrachat.dto.response.AuthResponse;
import com.api.intrachat.dto.generics.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.prefix}")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                          IJwtService jwtService,
                          CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/auth")
    public ResponseEntity<GeneralResponse<?>> auth(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String accessToken = jwtService.generarAccessToken(userDetails);
            String rolUsuario = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(null);

            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseConstruct.generarRespuestaExitosa(new AuthResponse(accessToken, rolUsuario))
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseConstruct.generarRespuestaConError(
                            "Credenciales inválidas, intente nuevamente."
                    ));
        } catch (DisabledException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResponseConstruct.generarRespuestaConError(
                            "El usuario se encuentra inactivo, contacte a un administrador."
                    ));
        }
    }

}
