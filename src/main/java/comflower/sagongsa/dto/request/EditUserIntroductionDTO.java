package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "사용자 소개 수정 DTO")
public class EditUserIntroductionDTO {
    @Schema(description = "사용자 소개", example = "안녕하세요, 저는 개발자입니다.")
    private String introduction;
}
