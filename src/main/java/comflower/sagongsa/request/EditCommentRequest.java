package comflower.sagongsa.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EditCommentRequest {
    private String content;
}
