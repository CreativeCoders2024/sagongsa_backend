package comflower.sagongsa.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidPasswordException extends IllegalArgumentException {
    private final String password;
}
