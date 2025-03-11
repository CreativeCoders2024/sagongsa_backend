package comflower.sagongsa.validator;

import comflower.sagongsa.request.EditCommentRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EditCommentValidator implements Validator {
    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        var request = (EditCommentRequest) target;

        if (request.getContent() == null || request.getContent().isBlank()) {
            errors.rejectValue("content", "content.required", "내용을 입력해주세요.");
        }
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return EditCommentRequest.class.equals(clazz);
    }
}
