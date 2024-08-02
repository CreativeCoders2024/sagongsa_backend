package comflower.sagongsa.dto.response;

import lombok.Builder;

@Builder
public class SignupResponse {
    private Long userId;
    private String token;
}
