package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.Errors;

import java.net.URI;

@Getter
@Builder
@Schema(description = "대회 수정 DTO")
public class EditContestRequest implements Request {
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
    private int topic;

    @Override
    public void validate(Errors errors) {
        if (title == null || title.isBlank()) {
            errors.rejectValue("title", "title.required", "제목을 입력해주세요.");
        }
        if (link == null || link.isBlank() || isMalformedURL(link)) {
            errors.rejectValue("link", "link.required", "링크를 입력해주세요.");
        }
        if (prize == null || prize.isBlank()) {
            errors.rejectValue("prize", "prize.required", "상품을 입력해주세요.");
        }
        if (startedAt < 0) {
            errors.rejectValue("startedAt", "startedAt.invalid", "올바르지 않은 시작일입니다.");
        }
        if (endedAt < startedAt) {
            errors.rejectValue("endedAt", "endedAt.invalid", "종료일은 시작일보다 미래여야 합니다.");
        }
        if (thumbnail == null || thumbnail.isBlank() || isMalformedURL(thumbnail)) {
            errors.rejectValue("thumbnail", "thumbnail.required", "썸네일을 입력해주세요.");
        }
    }

    private boolean isMalformedURL(String url) {
        try {
            new URI(url).toURL();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
