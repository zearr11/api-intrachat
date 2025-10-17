package com.api.intrachat.utils.components;

import com.api.intrachat.services.impl.user.CustomUserDetailsService;
import com.api.intrachat.services.interfaces.others.IJwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final IJwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtChannelInterceptor(IJwtService jwtService,
                                 CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) return message;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader = accessor.getFirstNativeHeader("Authorization");
            String token;
            String email;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AuthenticationCredentialsNotFoundException("Authorization header missing.");
            }

            token = authHeader.substring(7);

            if (!jwtService.validateJwtToken(token)) {
                throw new BadCredentialsException("Invalid token.");
            }

            email = jwtService.extractEmail(token);

            if (email != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (userDetails != null
                        && jwtService.isTokenValid(token, userDetails)
                        && jwtService.isAccessToken(token)
                ) {
                    Authentication auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    accessor.setUser(auth);
                }
            }

        }

        return message;
    }

}
