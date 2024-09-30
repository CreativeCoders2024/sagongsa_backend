package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Tag(name = "user")
@Schema(description = "사용자 소개 수정 DTO")
public class EditUserIntroductionDTO {
    @Schema(description = "사용자 소개", example = "안녕하세요, 저는 개발자입니다.")
    private String introduction;
}
