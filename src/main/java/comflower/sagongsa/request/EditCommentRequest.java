package comflower.sagongsa.request;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class EditCommentRequest implements Request {
    private String content;

    @Override
    public void validate(Errors errors) {
        if (content == null || content.isBlank()) {
            errors.rejectValue("content", "content.required", "내용을 입력해주세요.");
        }
    }
}
