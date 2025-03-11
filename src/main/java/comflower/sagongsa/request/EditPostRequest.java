package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EditPostRequest {
    private String title;
    private String content;
    private long authorId;
    private int memberCount;
    private int maxMemberCount;
    private int topic;
    private long endedAt;
}
