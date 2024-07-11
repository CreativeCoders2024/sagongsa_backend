package comflower.sagongsa.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupDTO {
    private String id;
    private String pw;
    private String nickname;
    private String email;
}
