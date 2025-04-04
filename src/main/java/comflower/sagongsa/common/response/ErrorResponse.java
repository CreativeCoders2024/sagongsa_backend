package comflower.sagongsa.common.response;

import comflower.sagongsa.common.exception.ExceptionType;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ErrorResponse {
    private final int code;
    private final String message;

    public ErrorResponse(ExceptionType errorType) {
        this.code = errorType.getCode();
        this.message = errorType.getMessage();
    }

    // 데이터를 같이 넣어줄 필요가 없으면 이 친구가 쓰일거고
    public static ResponseEntity<ErrorResponse> entity(ExceptionType errorType) {
        return ResponseEntity.status(errorType.getHttpStatus()).body(new ErrorResponse(errorType));
    }

    // 데이터를 같이 넣어줘야 하는 경우에는 이 친구가 쓰일거임
    public static <T> ResponseEntity<ErrorResponse> entity(ExceptionType exceptionType, T data) {
        return ResponseEntity.status(exceptionType.getHttpStatus()).body(new ErrorDataResponse<>(exceptionType, data));
    }
}
