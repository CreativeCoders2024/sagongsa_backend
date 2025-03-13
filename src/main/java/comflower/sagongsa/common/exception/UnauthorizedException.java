package comflower.sagongsa.common.exception;

import comflower.sagongsa.common.response.ErrorResponse;
import org.springframework.http.ResponseEntity;

public class UnauthorizedException extends KnownException {
    @Override
    public ResponseEntity<ErrorResponse> asResponse() {
        return ErrorResponse.entity(ExceptionType.UNAUTHORIZED);
    }
}
