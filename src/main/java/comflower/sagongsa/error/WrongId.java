package comflower.sagongsa.error;

public class WrongId extends IllegalArgumentException {
    private final String id;

    public WrongId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
