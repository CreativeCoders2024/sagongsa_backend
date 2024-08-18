package comflower.sagongsa.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class CreateCommentDTO {

    private String content;
    private Long parentId;


}
