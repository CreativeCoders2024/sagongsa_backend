package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

@Schema(description = "사용자 수정 DTO")
public class EditUserDTO {
    @Schema(description = "사용자 닉네임", example = "닉네임")
    private String nickname;
    @Schema(description = "사용자 분야", example = "1")
    private Integer field;
    @Schema(description = "사용자 소개", example = "안녕하세요, 저는 개발자입니다.")
    private String introduction;
}
