package comflower.sagongsa.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditUserFieldDTO {
    private Long userId;
    private int field;
}
