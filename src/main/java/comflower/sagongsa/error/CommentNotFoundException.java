package comflower.sagongsa.error;

import lombok.Getter;

@Getter
public class CommentNotFoundException extends IllegalArgumentException {
    private final Long commentId;

    public CommentNotFoundException(Long commentId) {
        super("Comment not found with id: " + commentId);
        this.commentId = commentId;
    }
}
