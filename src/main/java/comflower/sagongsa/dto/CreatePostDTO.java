package comflower.sagongsa.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreatePostDTO {
    private Long contestId;
    private String title;
    private String content;
    private int max;
    private int ppl;
    private int desired_field;
    private LocalDateTime endedAt;
}
