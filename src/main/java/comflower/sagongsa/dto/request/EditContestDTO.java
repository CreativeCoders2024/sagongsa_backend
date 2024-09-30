package comflower.sagongsa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Tag(name = "contest")
@Schema(description = "대회 수정 DTO")
public class EditContestDTO {
    @Schema(description = "대회 제목", example = "대회 제목")
    private String title;
    @Schema(description = "대회 이미지", example = "대회 이미지")
    private String img;
    @Schema(description = "대회 상금", example = "대회 상금")
    private String prize;
    @Schema(description = "대회 시작 시간", example = "2024-01-01 00:00:00")
    private LocalDateTime startedAt; //  String에서 LocalDateTime 타입으로 변경하면 오류 사라짐
    @Schema(description = "대회 종료 시간", example = "2024-01-01 00:00:00")
    private LocalDateTime endedAt;   //  String에서 LocalDateTime 타입으로 변경하면 오류 사라짐
    @Schema(description = "대회 링크", example = "대회 링크")
    private String link;
    @Schema(description = "대회 분야", example = "대회 분야")
    private Long field;
}
