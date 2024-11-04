package comflower.sagongsa.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDTO {
    private String username; // 로그인 시 사용할 아이디 필드
    private String password; // 로그인 시 사용할 비밀번호 필드
}
