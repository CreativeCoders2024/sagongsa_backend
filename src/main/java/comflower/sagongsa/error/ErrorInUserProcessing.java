package comflower.sagongsa.error;

public class ErrorInUserProcessing extends IllegalArgumentException {
    private final Long userId;

    public ErrorInUserProcessing(String message, Long userId) {
        super(message);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
