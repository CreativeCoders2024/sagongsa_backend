package comflower.sagongsa.auth.response;

import comflower.sagongsa.user.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticatedResponse {
    private final User user;
    private String token;
}
