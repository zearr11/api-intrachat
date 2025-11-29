package com.api.intrachat.utils.interceptors;

import com.api.intrachat.services.impl.CustomUserDetailsService;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.services.interfaces.other.IJwtService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final IJwtService jwtService;
    private final IUsuarioService usuarioService;
    private final CustomUserDetailsService userDetailsService;

    public JwtChannelInterceptor(IJwtService jwtService,
                                 IUsuarioService usuarioService,
                                 CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor
                .getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) return message;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            String token;
            String email;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new AuthenticationCredentialsNotFoundException("Authorization header missing.");
            }

            token = authHeader.substring(7);

            if (!jwtService.esJwtValido(token)) {
                throw new BadCredentialsException("Invalid token.");
            }

            email = jwtService.extraerEmail(token);

            if (email != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                Long idUser  = usuarioService.obtenerUsuarioPorEmail(email).getId();

                if (userDetails != null
                        && jwtService.esUnTokenValido(token, userDetails)
                        && jwtService.esUnAccessToken(token)
                ) {
                    Authentication auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    accessor.setUser(idUser::toString);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        return message;
    }

}