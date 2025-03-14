package comflower.sagongsa.comment.request;

import comflower.sagongsa.common.request.Request;
import comflower.sagongsa.common.util.ValidationUtils;
import lombok.Data;
import org.springframework.validation.Errors;

@Data
public class EditCommentRequest implements Request {
    private String content;

    @Override
    public void validate(Errors errors) {
        if (ValidationUtils.isBlank(content)) {
            errors.rejectValue("content", "content.required", "내용을 입력해주세요.");
        }
    }
}
