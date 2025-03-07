package comflower.sagongsa.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private String content;
    private Long parentId;
}
