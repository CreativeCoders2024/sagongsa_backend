package comflower.sagongsa.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    // 너가 본 예시대로 enum 으로 클래스 만들어서 다양한 에러들을 여기다가 나열함
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, 10000, "User already exists"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, 400, "Wrong Password"),
    WRONG_ID(HttpStatus.BAD_REQUEST, 400, "Wrong ID");
    // 나열중 ....

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    // 프론트쪽에 반환되는 형식 !
    ErrorType(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}