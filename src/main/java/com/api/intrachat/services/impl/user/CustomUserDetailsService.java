package com.api.intrachat.services.impl.user;

import com.api.intrachat.exceptions.errors.ErrorUsernameNotFoundException;
import com.api.intrachat.models.user.CustomUserDetails;
import com.api.intrachat.repositories.user.UserRepository;
import com.api.intrachat.utils.constants.ValuesUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(
                        () -> new ErrorUsernameNotFoundException(ValuesUser.emailNotFoundMessage(email))
                );
    }

}
