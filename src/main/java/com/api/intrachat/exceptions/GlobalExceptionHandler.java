package com.api.intrachat.exceptions;

import com.api.intrachat.exceptions.errors.*;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.dto.response.general.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorException400.class)
    public ResponseEntity<GeneralResponse<?>> error400(ErrorException400 exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseConstruct.errorGeneric(exception.getMessage())
        );
    }

    @ExceptionHandler(ErrorException401.class)
    public ResponseEntity<GeneralResponse<?>> error401(ErrorException401 exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseConstruct.errorGeneric(exception.getMessage())
        );
    }

    @ExceptionHandler(ErrorException404.class)
    public ResponseEntity<GeneralResponse<?>> error404(ErrorException404 exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseConstruct.errorGeneric(exception.getMessage())
        );
    }

    @ExceptionHandler(ErrorException409.class)
    public ResponseEntity<GeneralResponse<?>> error409(ErrorException409 exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseConstruct.errorGeneric(exception.getMessage())
        );
    }

    @ExceptionHandler(ErrorUsernameNotFoundException.class)
    public ResponseEntity<GeneralResponse<?>> usernameNotFoundException(ErrorUsernameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseConstruct.errorGeneric(exception.getMessage())
        );
    }

}
