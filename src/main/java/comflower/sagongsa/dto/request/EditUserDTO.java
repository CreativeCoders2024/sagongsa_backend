package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

@Schema(description = "사용자 수정 DTO")
public class EditUserDTO {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;  //토큰 시 수정 필요
    @Schema(description = "사용자 비밀번호", example = "1234")
    private String pw;
    @Schema(description = "사용자 닉네임", example = "닉네임")
    private String nickname;
}
