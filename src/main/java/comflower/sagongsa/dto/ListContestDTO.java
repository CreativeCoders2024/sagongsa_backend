package comflower.sagongsa.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ListContestDTO {
    private Long contestId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
