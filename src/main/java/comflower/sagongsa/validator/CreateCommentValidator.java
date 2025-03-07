package comflower.sagongsa.validator;

import comflower.sagongsa.request.CreateCommentRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateCommentValidator implements Validator {
    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        var request = (CreateCommentRequest) target;

        if (request.getContent() == null || request.getContent().isBlank()) {
            errors.rejectValue("content", "content.required", "내용을 입력해주세요.");
        }

        if (request.getParentId() != null && request.getParentId() < 1) {
            errors.rejectValue("parentId", "parentId.invalid", "부모 댓글 ID가 올바르지 않습니다.");
        }
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return CreateCommentRequest.class.equals(clazz);
    }
}
