package com.api.intrachat.utils.exceptions.errors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ErrorUsernameNotFoundException extends UsernameNotFoundException {
    public ErrorUsernameNotFoundException(String message) {
        super(message);
    }
}
