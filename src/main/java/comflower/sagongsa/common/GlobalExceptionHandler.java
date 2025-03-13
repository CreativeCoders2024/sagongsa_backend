package comflower.sagongsa.common;

import comflower.sagongsa.common.exception.KnownException;
import comflower.sagongsa.common.response.ErrorResponse;
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
