package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "대회 생성 DTO")
public class CreateContestRequest {
    private String title;
    private String link;
    private String prize;
    private long topic;
    private long startedAt;
    private long endedAt;
    private String thumbnail;
}
