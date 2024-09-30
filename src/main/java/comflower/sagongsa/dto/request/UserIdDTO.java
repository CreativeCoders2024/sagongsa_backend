package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Tag(name = "user")
@Schema(description = "사용자 ID DTO")
public class UserIdDTO {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Builder
    public UserIdDTO(Long userId) {
        this.userId = userId;
    }
}
