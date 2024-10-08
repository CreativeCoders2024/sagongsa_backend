package comflower.sagongsa.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EditPostDTO {
    private Long userId;
    private Long contestId;
    private String title;
    private String content;
    private Integer max;
    private Integer ppl;
    private Integer desiredField;
    private LocalDateTime endedAt;
}
