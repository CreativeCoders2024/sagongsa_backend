package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "사용자 분야 수정 DTO")
public class EditUserFieldDTO {
    @Schema(description = "사용자 분야", example = "1")
    private int field;
}
