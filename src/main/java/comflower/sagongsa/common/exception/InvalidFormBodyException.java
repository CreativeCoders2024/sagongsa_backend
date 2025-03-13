package comflower.sagongsa.common.exception;

import comflower.sagongsa.common.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RequiredArgsConstructor
public class InvalidFormBodyException extends KnownException {
    private final Map<String, String> errors;

    @Override
    public ResponseEntity<ErrorResponse> asResponse() {
        return ErrorResponse.entity(ExceptionType.INVALID_FORM_BODY, errors);
    }
}
