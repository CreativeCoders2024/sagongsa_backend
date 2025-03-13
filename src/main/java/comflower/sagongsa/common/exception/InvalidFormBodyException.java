package comflower.sagongsa.common.exception;

import comflower.sagongsa.common.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InvalidFormBodyException extends KnownException {
    private final Map<String, String> errors;

    public InvalidFormBodyException(BindingResult bindingResult) {
        this.errors = bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        f -> f.getDefaultMessage() == null ? "" : f.getDefaultMessage()
                ));
    }

    @Override
    public ResponseEntity<ErrorResponse> asResponse() {
        return ErrorResponse.entity(ExceptionType.INVALID_FORM_BODY, errors);
    }
}
