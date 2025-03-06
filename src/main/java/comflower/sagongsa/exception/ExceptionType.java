package comflower.sagongsa.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    UNKNOWN_USER(HttpStatus.NOT_FOUND, 10000, "User not found"),
    UNKNOWN_CONTEST(HttpStatus.NOT_FOUND, 10001, "Contest not found"),
    UNKNOWN_POST(HttpStatus.NOT_FOUND, 10002, "Post not found"),
    UNKNOWN_COMMENT(HttpStatus.NOT_FOUND, 10003, "Comment not found"),
    INVALID_FORM_BODY(HttpStatus.BAD_REQUEST, 10012, "Invalid form body"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 20000, "You should be logged in to access this resource"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 20001, "You do not have permission to access this resource"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, 30000, "User already exists"),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, 30001, "Invalid credentials");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
