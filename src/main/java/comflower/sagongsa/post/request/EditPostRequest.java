package comflower.sagongsa.post.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "게시글 수정 DTO")
public class EditPostRequest {
    @NotBlank
    @Schema(description = "게시글 제목", example = "게시글 제목")
    private String title;

    @NotBlank
    @Schema(description = "게시글 내용", example = "게시글 내용")
    private String content;

    @NotNull
    @Schema(description = "사용자 ID", example = "1")
    private Long authorId;

    @Schema(description = "현재 인원", example = "5")
    private int memberCount;

    @Schema(description = "최대 인원", example = "10")
    private int maxMemberCount;

    @Schema(description = "원하는 분야", example = "1")
    private int topic;

    @Schema(description = "게시글 종료 시간", example = "2024-01-01 00:00:00")
    private long endedAt;
}
