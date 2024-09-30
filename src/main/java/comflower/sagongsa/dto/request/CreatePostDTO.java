package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Tag(name = "post")
@Schema(description = "게시글 생성 DTO")
public class CreatePostDTO {
    @Schema(description = "대회 ID", example = "1")
    private Long contestId;
    @Schema(description = "게시글 제목", example = "게시글 제목")
    private String title;
    @Schema(description = "게시글 내용", example = "게시글 내용")
    private String content;
    @Schema(description = "최대 인원", example = "10")
    private int max;
    @Schema(description = "현재 인원", example = "5")
    private int ppl;
    @Schema(description = "원하는 분야", example = "1")
    private int desired_field;
    @Schema(description = "게시글 종료 시간", example = "2024-01-01 00:00:00")
    private LocalDateTime endedAt;
}
