package comflower.sagongsa.dto.response;

public class ErrorDataResponse<T> extends ErrorResponse {
    private final T data;

    public ErrorDataResponse(ErrorType errorType, T data) {
        super(errorType);
        this.data = data;
    }
}
