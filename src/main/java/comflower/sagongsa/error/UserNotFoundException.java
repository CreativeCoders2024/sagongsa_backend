package comflower.sagongsa.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserNotFoundException extends IllegalArgumentException {
    private final Long userId;
}
