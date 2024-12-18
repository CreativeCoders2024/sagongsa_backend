package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Tags(value = {@Tag(name = "user"), @Tag(name = "auth")})
@Schema(description = "로그인 DTO")
public class LoginDTO {
    @Schema(description = "사용자 ID", example = "abc123")
    private String id;
    private String pw;
}
