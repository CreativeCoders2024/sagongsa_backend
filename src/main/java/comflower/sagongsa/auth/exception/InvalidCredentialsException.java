package comflower.sagongsa.auth.exception;

import comflower.sagongsa.common.exception.ExceptionType;
import comflower.sagongsa.common.exception.KnownException;
import comflower.sagongsa.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class InvalidCredentialsException extends KnownException {
    @Override
    public ResponseEntity<ErrorResponse> asResponse() {
        return ErrorResponse.entity(ExceptionType.INVALID_CREDENTIALS);
    }
}
