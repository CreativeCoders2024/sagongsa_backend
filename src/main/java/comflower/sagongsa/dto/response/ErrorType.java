package comflower.sagongsa.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, 10000, "User already exists"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, 10001, "Wrong Password"),
    WRONG_ID(HttpStatus.BAD_REQUEST, 10002, "Wrong ID"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 10003, "User Not Found"),
    ERROR_IN_PROCESSING(HttpStatus.CONFLICT, 10004, "Errors in processing");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ErrorType(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}