package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;

@Getter
@Tag(name = "comment")
@Schema(description = "댓글 생성 DTO")
public class CreateCommentDTO {
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String content;
    @Schema(description = "부모 댓글 ID", example = "1")
    private Long parentId;
}
