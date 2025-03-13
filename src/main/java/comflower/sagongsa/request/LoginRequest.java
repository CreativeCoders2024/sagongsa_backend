package comflower.sagongsa.request;

import lombok.Getter;

@Getter
public class LoginRequest implements Request {
    private String username;
    private String password;

    @Override
    public void validate(org.springframework.validation.Errors errors) {
        if (username == null || username.isBlank()) {
            errors.rejectValue("username", "username.required", "아이디를 입력해주세요.");
        }
        if (password == null || password.isBlank()) {
            errors.rejectValue("password", "password.required", "비밀번호를 입력해주세요.");
        }
    }
}
