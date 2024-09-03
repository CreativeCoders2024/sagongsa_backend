package comflower.sagongsa.error;

import lombok.Getter;

@Getter
public class UserNotFoundException extends IllegalArgumentException {
    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
        this.userId = userId;
    }
}
