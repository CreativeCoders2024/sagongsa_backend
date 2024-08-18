package comflower.sagongsa.dto.response;

import lombok.Getter;

@Getter
public class ErrorDataResponse<T> extends ErrorResponse {
    private final T data;

    public ErrorDataResponse(ErrorType errorType, T data) {
        super(errorType);
        this.data = data;
    }
}