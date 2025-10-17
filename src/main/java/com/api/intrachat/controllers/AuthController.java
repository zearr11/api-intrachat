package com.api.intrachat.controllers;

import com.api.intrachat.services.impl.user.CustomUserDetailsService;
import com.api.intrachat.services.interfaces.others.IJwtService;
import com.api.intrachat.services.interfaces.user.IUserService;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.dto.auth.AuthRequest;
import com.api.intrachat.utils.dto.auth.AuthResponse;
import com.api.intrachat.utils.dto.response.general.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final IUserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          IJwtService jwtService,
                          CustomUserDetailsService userDetailsService, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<GeneralResponse<?>> auth(@RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.success(
                new AuthResponse(accessToken, refreshToken))
        );

    }
}
