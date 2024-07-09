package comflower.sagongsa.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ListPostDTO {
    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
    private int desiredField; //분야정보까지 추가
    // api랑 다름
}
