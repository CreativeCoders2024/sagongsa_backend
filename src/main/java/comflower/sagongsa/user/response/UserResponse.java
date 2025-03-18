package comflower.sagongsa.user.response;

import comflower.sagongsa.user.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final long id;
    private final String username;
    private final String nickname;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}
