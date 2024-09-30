package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Tag(name = "user")
@Schema(description = "사용자 분야 수정 DTO")
public class EditUserFieldDTO {
    @Schema(description = "사용자 분야", example = "1")
    private int field;
}
