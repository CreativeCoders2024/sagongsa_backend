package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "회원가입 DTO")
public class SignupDTO {
    @Schema(description = "사용자 아이디", example = "1")
    private String username;
    @Schema(description = "사용자 비밀번호", example = "1234")
    private String password;
    @Schema(description = "사용자 닉네임", example = "닉네임")
    private String nickname;
    @Schema(description = "사용자 이메일", example = "example@example.com")
    private String email;
}
