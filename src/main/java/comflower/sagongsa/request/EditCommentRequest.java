package comflower.sagongsa.request;

import lombok.Data;
import org.springframework.validation.Errors;

@Data
public class EditCommentRequest implements Request {
    private String content;

    @Override
    public void validate(Errors errors) {
        if (content == null || content.isBlank()) {
            errors.rejectValue("content", "content.required", "내용을 입력해주세요.");
        }
    }
}
