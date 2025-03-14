package comflower.sagongsa.contest.request;

import comflower.sagongsa.common.request.Request;
import comflower.sagongsa.common.util.ValidationUtils;
import lombok.Data;
import org.springframework.validation.Errors;

@Data
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
        if (ValidationUtils.isBlank(title)) {
            errors.rejectValue("title", "title.required", "제목을 입력해주세요.");
        }
        if (ValidationUtils.isBlank(link) || !ValidationUtils.isURL(link)) {
            errors.rejectValue("link", "link.required", "링크를 입력해주세요.");
        }
        if (ValidationUtils.isBlank(prize)) {
            errors.rejectValue("prize", "prize.required", "상품을 입력해주세요.");
        }
        if (startedAt < 0) {
            errors.rejectValue("startedAt", "startedAt.invalid", "올바르지 않은 시작일입니다.");
        }
        if (endedAt < startedAt) {
            errors.rejectValue("endedAt", "endedAt.invalid", "종료일은 시작일보다 미래여야 합니다.");
        }
        if (ValidationUtils.isBlank(thumbnail) || !ValidationUtils.isURL(thumbnail)) {
            errors.rejectValue("thumbnail", "thumbnail.required", "썸네일을 입력해주세요.");
        }
    }
}
