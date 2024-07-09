package comflower.sagongsa.dto;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class CheckPostDTO {
    private Long postId;
    private Long userId;
    private Long contestId;
    private String title;
    private String content;
    private int max;
    private int ppl;
    private int desiredField;
    private LocalDateTime createdAt;
    private LocalDateTime endedAt;
    // api랑 이름이 다르고 컬럼 값도 다름
}
