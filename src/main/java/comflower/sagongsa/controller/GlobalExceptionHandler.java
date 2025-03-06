package comflower.sagongsa.controller;

import comflower.sagongsa.response.ErrorResponse;
import comflower.sagongsa.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(KnownException.class)
    public ResponseEntity<ErrorResponse> handleKnownException(KnownException e) {
        return e.asResponse();
    }
}
