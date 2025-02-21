package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "로그인 DTO")
public class LoginDTO {
    @Schema(description = "사용자 아이디", example = "1")
    private String username;
    private String password;
}
