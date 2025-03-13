package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.Errors;

import java.net.URI;

@Getter
@Builder
@Schema(description = "대회 수정 DTO")
public class EditContestRequest implements Request {
    private String title;
    private String link;
    private String prize;
    private int topic;
    private long startedAt;
    private long endedAt;
    private String thumbnail;

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
