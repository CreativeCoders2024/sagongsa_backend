package comflower.sagongsa.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditUserDTO {
    private Long userId;  //토큰 시 수정 필요
    private String pw;
    private String nickname;
}
