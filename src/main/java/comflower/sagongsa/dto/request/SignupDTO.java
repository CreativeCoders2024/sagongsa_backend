package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Tags(value = {@Tag(name = "user"), @Tag(name = "auth")})
@Schema(description = "회원가입 DTO")
public class SignupDTO {
    @Schema(description = "사용자 ID", example = "1")
    private String id;
    @Schema(description = "사용자 비밀번호", example = "1234")
    private String pw;
    @Schema(description = "사용자 닉네임", example = "닉네임")
    private String nickname;
    @Schema(description = "사용자 이메일", example = "example@example.com")
    private String email;
}
