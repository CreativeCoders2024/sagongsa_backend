package comflower.sagongsa.common.exception;

import comflower.sagongsa.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class UnknownUserException extends KnownException {
    @Override
    public ResponseEntity<ErrorResponse> asResponse() {
        return ErrorResponse.entity(ExceptionType.UNKNOWN_USER);
    }
}
