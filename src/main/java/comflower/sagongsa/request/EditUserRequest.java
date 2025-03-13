package comflower.sagongsa.request;

import lombok.Data;

@Data
public class EditUserRequest implements Request {
    private String nickname;
    private Integer field;
    private String introduction;

    @Override
    public void validate(org.springframework.validation.Errors errors) {
        if (nickname == null || nickname.isBlank()) {
            errors.rejectValue("nickname", "nickname.required", "닉네임을 입력해주세요.");
        }
        if (introduction == null || introduction.isBlank()) {
            errors.rejectValue("introduction", "introduction.required", "소개를 입력해주세요.");
        }
    }
}
