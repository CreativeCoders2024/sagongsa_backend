package comflower.sagongsa.dto;

import lombok.Getter;

@Getter
public class CreateCommentDTO {
    private String content;
    private int parent;
}
