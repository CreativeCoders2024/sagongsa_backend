package comflower.sagongsa.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "회원가입 응답 DTO")
public class SignupResponse {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    @Schema(description = "JWT 토큰", example = "token")
    private String token;
}
