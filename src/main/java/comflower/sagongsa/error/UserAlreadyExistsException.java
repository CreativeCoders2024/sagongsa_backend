package comflower.sagongsa.error;

public class UserAlreadyExistsException extends IllegalArgumentException {
    private final String id;

    public UserAlreadyExistsException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
