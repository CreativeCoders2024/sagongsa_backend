package comflower.sagongsa.dto.request;

import lombok.Getter;

@Getter
public class CreateCommentDTO {
    private String content;
    private int parent;
}
