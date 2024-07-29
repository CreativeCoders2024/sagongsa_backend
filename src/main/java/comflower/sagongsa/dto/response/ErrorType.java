package comflower.sagongsa.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already exists");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
