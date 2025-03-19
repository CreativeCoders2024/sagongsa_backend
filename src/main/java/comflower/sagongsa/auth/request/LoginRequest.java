package comflower.sagongsa.auth.request;

import comflower.sagongsa.common.request.Request;
import comflower.sagongsa.common.util.ValidationUtils;
import lombok.Data;

@Data
public class LoginRequest implements Request {
    private String username;
    private String password;

    @Override
    public void validate(org.springframework.validation.Errors errors) {
        if (ValidationUtils.isBlank(username)) {
            errors.rejectValue("username", "username.required", "아이디를 입력해주세요.");
        }
        if (ValidationUtils.isBlank(password)) {
            errors.rejectValue("password", "password.required", "비밀번호를 입력해주세요.");
        }
    }
}
