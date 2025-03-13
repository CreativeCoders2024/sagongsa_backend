package comflower.sagongsa.request;

import lombok.Data;
import org.springframework.validation.Errors;

@Data
public class CreateCommentRequest implements Request {
    private String content;
    private Long parentId;

    @Override
    public void validate(Errors errors) {
        if (content == null || content.isBlank()) {
            errors.rejectValue("content", "content.required", "내용을 입력해주세요.");
        }

        if (parentId != null && parentId < 1) {
            errors.rejectValue("parentId", "parentId.invalid", "부모 댓글 ID가 올바르지 않습니다.");
        }
    }
}
