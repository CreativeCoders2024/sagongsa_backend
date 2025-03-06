package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "댓글 수정 DTO")
public class EditCommentRequest {
    @NotBlank
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;
}
