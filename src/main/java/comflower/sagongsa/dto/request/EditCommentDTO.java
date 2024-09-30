package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 수정 DTO")
public class EditCommentDTO {
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;
}
