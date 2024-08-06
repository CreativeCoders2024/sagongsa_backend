package comflower.sagongsa.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InquiryOfUserResponse {
    private String profile_img;
    private int field;
    private String introduction;
}
