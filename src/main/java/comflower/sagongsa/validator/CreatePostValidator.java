package comflower.sagongsa.validator;

import comflower.sagongsa.request.CreatePostRequest;
import comflower.sagongsa.util.TagHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CreatePostValidator implements Validator {
    private final TagHelper tagHelper;

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        CreatePostRequest request = (CreatePostRequest) target;

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            errors.rejectValue("title", "title.required", "제목을 입력해주세요.");
        }
        if (request.getContent() == null || request.getContent().isBlank()) {
            errors.rejectValue("content", "content.required", "내용을 입력해주세요.");
        }
        if (request.getMemberCount() < 1) {
            errors.rejectValue("memberCount", "memberCount", "참여 인원은 1명 이상이어야 합니다.");
        }
        if (request.getMaxMemberCount() < request.getMemberCount()) {
            errors.rejectValue("maxMemberCount", "maxMemberCount", "최대 참여 인원은 현재 참여 인원보다 많아야 합니다.");
        }
        if (!tagHelper.isTag(TagHelper.POST_TAGS, request.getTopic())) {
            errors.rejectValue("topic", "topic.invalid", "올바르지 않은 주제입니다.");
        }
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return CreatePostRequest.class.equals(clazz);
    }
}
