package comflower.sagongsa.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIdDTO {
    private Long userId;

    @Builder
    public UserIdDTO(Long userId) {
        this.userId = userId;
    }
}
