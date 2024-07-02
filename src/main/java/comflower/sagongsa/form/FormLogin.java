package comflower.sagongsa.form;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FormLogin {
    private String id;
    private String pw;
}