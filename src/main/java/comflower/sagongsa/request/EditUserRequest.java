package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EditUserRequest implements Request {
    @Schema(description = "사용자 닉네임", example = "닉네임")
    private String nickname;
    @Schema(description = "사용자 분야", example = "1")
    private Integer field;
    @Schema(description = "사용자 소개", example = "안녕하세요, 저는 개발자입니다.")
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
