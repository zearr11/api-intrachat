package com.api.intrachat.utils.components;

import com.api.intrachat.services.interfaces.IJwtService;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final IJwtService jwtService;

    public JwtChannelInterceptor(IJwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) return message;

        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                throw new AuthenticationCredentialsNotFoundException("Authorization header missing.");
            }

            token = token.substring(7);

            if (!jwtService.validateJwtToken(token)) {
                throw new BadCredentialsException("Invalid token.");
            }

            String email = jwtService.extractEmail(token);
            List<SimpleGrantedAuthority> roles = jwtService.extractRoles(token);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    email, null, roles
            );

            accessor.setUser(auth);
        }

        return message;
    }

}
