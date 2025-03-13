package comflower.sagongsa.request;

import lombok.Data;

@Data
public class SignupRequest implements Request {
    private String username;
    private String password;
    private String nickname;
    private String email;

    @Override
    public void validate(org.springframework.validation.Errors errors) {
        if (username == null || username.isBlank()) {
            errors.rejectValue("username", "username.required", "아이디를 입력해주세요.");
        }
        if (password == null || password.isBlank()) {
            errors.rejectValue("password", "password.required", "비밀번호를 입력해주세요.");
        }
        if (nickname == null || nickname.isBlank()) {
            errors.rejectValue("nickname", "nickname.required", "닉네임을 입력해주세요.");
        }
        if (email == null || email.isBlank()) {
            errors.rejectValue("email", "email.required", "이메일을 입력해주세요.");
        }
    }
}
