package comflower.sagongsa.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, 10000, "User already exists");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ErrorType(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
