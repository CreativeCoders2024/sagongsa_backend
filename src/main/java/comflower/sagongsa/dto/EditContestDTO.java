package comflower.sagongsa.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class EditContestDTO {
    private  Long userId; //수정 불가지만 식별용??
    private String title;
    private String img;
    private String prize;
    private LocalDateTime startedAt; //  String에서 LocalDateTime 타입으로 변경하면 오류 사라짐
    private LocalDateTime endedAt;   //  String에서 LocalDateTime 타입으로 변경하면 오류 사라짐
    private String link;
    private Long field;
}
