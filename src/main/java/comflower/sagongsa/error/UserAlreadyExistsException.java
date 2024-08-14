package comflower.sagongsa.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAlreadyExistsException extends IllegalArgumentException {
    private final String id;
}
