package comflower.sagongsa.user.request;

import comflower.sagongsa.common.request.Request;
import comflower.sagongsa.common.util.ValidationUtils;
import lombok.Data;

@Data
public class EditUserRequest implements Request {
    private String nickname;
    private Integer field;
    private String introduction;

    @Override
    public void validate(org.springframework.validation.Errors errors) {
        if (ValidationUtils.isBlank(nickname)) {
            errors.rejectValue("nickname", "nickname.required", "닉네임을 입력해주세요.");
        }
        if (ValidationUtils.isBlank(introduction)) {
            errors.rejectValue("introduction", "introduction.required", "소개를 입력해주세요.");
        }
    }
}
