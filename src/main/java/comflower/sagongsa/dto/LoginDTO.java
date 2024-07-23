package comflower.sagongsa.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDTO {
    private String id;
    private String pw;
}
