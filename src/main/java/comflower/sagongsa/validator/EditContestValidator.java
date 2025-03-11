package comflower.sagongsa.validator;

import comflower.sagongsa.request.EditContestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class EditContestValidator implements Validator {

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        var request = (EditContestRequest) target;

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            errors.rejectValue("title", "title.required", "제목을 입력해주세요.");
        }
        if (request.getLink() == null || request.getLink().isBlank() || isMalformedURL(request.getLink())) {
            errors.rejectValue("link", "link.required", "링크를 입력해주세요.");
        }
        if (request.getPrize() == null || request.getPrize().isBlank()) {
            errors.rejectValue("prize", "prize.required", "상품을 입력해주세요.");
        }
        if (request.getStartedAt() < 0) {
            errors.rejectValue("startedAt", "startedAt.invalid", "올바르지 않은 시작일입니다.");
        }
        if (request.getEndedAt() < request.getStartedAt()) {
            errors.rejectValue("endedAt", "endedAt.invalid", "종료일은 시작일보다 미래여야 합니다.");
        }
        if (request.getThumbnail() == null || request.getThumbnail().isBlank() || isMalformedURL(request.getThumbnail())) {
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

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return EditContestRequest.class.equals(clazz);
    }
}
