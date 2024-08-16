package comflower.sagongsa.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditIntroductionDTO {
    private Long userId;
    private String introduction;
}
