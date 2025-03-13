package comflower.sagongsa.common.exception;

import comflower.sagongsa.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

public abstract class KnownException extends RuntimeException {
    public abstract ResponseEntity<ErrorResponse> asResponse();
}
