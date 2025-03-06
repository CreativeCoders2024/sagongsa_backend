package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "대회 생성 DTO")
public class CreateContestRequest {
    @NotBlank
    @Schema(description = "대회 제목", example = "대회 제목")
    private String title;
    @NotBlank
    @Schema(description = "대회 이미지", example = "대회 이미지")
    private String thumbnail;
    @NotBlank
    @Schema(description = "대회 상금", example = "대회 상금")
    private String prize;
    @NotNull
    @Schema(description = "대회 시작 시간", example = "2024-01-01 00:00:00")
    private long startedAt;
    @NotNull
    @Schema(description = "대회 종료 시간", example = "2024-01-01 00:00:00")
    private long endedAt;
    @NotBlank
    @Schema(description = "대회 링크", example = "대회 링크")
    private String link;
    @NotNull
    @Schema(description = "대회 분야", example = "대회 분야")
    private Long field;
}
