package comflower.sagongsa.error;

public class UserNotFoundException extends IllegalArgumentException {
    private final Long user_id;

    public UserNotFoundException(Long user_id) {
        this.user_id = user_id;
    }

    public Long getUserId() {
        return user_id;
    }
}
