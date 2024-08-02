package comflower.sagongsa.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateContestDTO {
    private String title;
    private String img;
    private String prize;
    private LocalDateTime startedAt; //  String에서 LocalDateTime 타입으로 변경하면 오류 사라짐
    private LocalDateTime endedAt;   //  String에서 LocalDateTime 타입으로 변경하면 오류 사라짐
    private String link;
    private Long field;
}
