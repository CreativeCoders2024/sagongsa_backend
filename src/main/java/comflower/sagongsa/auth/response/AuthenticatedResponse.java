package comflower.sagongsa.auth.response;

import comflower.sagongsa.user.response.UserResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticatedResponse {
    private final UserResponse user;
    private final String token;
}
