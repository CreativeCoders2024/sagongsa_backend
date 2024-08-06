package comflower.sagongsa.error;

public class WrongIdException extends IllegalArgumentException {
    private final String id;

    public WrongIdException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
