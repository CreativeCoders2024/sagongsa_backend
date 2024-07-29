package comflower.sagongsa.dto.response;

import org.springframework.http.ResponseEntity;

public class ErrorResponse {
    private final String message;

    public ErrorResponse(ErrorType errorType) {
        this.message = errorType.getMessage();
    }

    public static ResponseEntity<ErrorResponse> entity(ErrorType errorType) {
        return ResponseEntity.status(errorType.getHttpStatus()).body(new ErrorResponse(errorType));
    }

    public static <T> ResponseEntity<ErrorResponse> entity(ErrorType errorType, T data) {
        return ResponseEntity.status(errorType.getHttpStatus()).body(new ErrorDataResponse<>(errorType, data));
    }
}
