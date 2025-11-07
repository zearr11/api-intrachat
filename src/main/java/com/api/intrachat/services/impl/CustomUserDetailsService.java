package com.api.intrachat.services.impl;

import com.api.intrachat.utils.exceptions.errors.ErrorUsernameNotFoundException;
import com.api.intrachat.models.CustomUserDetails;
import com.api.intrachat.repositories.general.UsuarioRepository;
import com.api.intrachat.utils.constants.UsuarioConstants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(
                        () -> new ErrorUsernameNotFoundException(UsuarioConstants.mensajeEmailNoExiste(email))
                );
    }

}
