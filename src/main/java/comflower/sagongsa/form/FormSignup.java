package comflower.sagongsa.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FormSignup {
    private String id;
    private String pw;
    private String nickname;
    private String email;
}
