package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "로그인 DTO")
public class LoginRequest {
    @Schema(description = "사용자 이름", example = "john.doe")
    private String username;
    private String password;
}
