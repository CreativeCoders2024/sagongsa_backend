package comflower.sagongsa.error;

public class WrongPassword extends IllegalArgumentException {
    private final String password;

    public WrongPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
