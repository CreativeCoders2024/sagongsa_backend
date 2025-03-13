package comflower.sagongsa.common.response;

import comflower.sagongsa.common.exception.ExceptionType;
import lombok.Getter;

@Getter
public class ErrorDataResponse<T> extends ErrorResponse {
    private final T data;

    public ErrorDataResponse(ExceptionType exceptionType, T data) {
        super(exceptionType);
        this.data = data;
    }
}
