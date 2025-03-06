package comflower.sagongsa.exception;

import comflower.sagongsa.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

public abstract class KnownException extends RuntimeException {
    public abstract ResponseEntity<ErrorResponse> asResponse();
}
