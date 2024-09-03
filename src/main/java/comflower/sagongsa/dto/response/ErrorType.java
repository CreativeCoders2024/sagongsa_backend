package comflower.sagongsa.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, 10000, "User already exists"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, 10001, "Wrong Password"),
    WRONG_ID(HttpStatus.BAD_REQUEST, 10002, "Wrong ID"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 10003, "User Not Found"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 10009, "Post not found"),
    INVALID_POST_DATA(HttpStatus.BAD_REQUEST, 10005, "Invalid post data"),
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, 10006, "Unauthorized access"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 10007, "Comment not found"),
    INVALID_COMMENT_DATA(HttpStatus.BAD_REQUEST, 10008, "Invalid comment data"),
    INVALID_CONTEST_DATA(HttpStatus.BAD_REQUEST, 10009, "Invalid contest data"),
    CONTEST_NOT_FOUND(HttpStatus.NOT_FOUND, 100010, "Contest not found"),
    INVALID_CONTEST_EDIT_DATA(HttpStatus.BAD_REQUEST, 10011, "Invalid contest data for edit");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ErrorType(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
