package comflower.sagongsa.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreatePostDTO {
    private Long userId;
    private Long contestId;
    private String title;
    private String content;
    private int max;
    private int ppl;
    private int desired_field;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
    // api랑 이름이 다름
}
