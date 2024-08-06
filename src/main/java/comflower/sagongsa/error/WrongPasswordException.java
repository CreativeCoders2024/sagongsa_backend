package comflower.sagongsa.error;

public class WrongPasswordException extends IllegalArgumentException {
    private final String password;

    public WrongPasswordException(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
