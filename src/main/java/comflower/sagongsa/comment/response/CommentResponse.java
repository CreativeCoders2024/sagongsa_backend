package comflower.sagongsa.comment.response;

import comflower.sagongsa.comment.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
    private final long id;
    private final long postId;
    private final long authorId;
    private final String content;
    private final long createdAt;
    private final Long editedAt;
    private final Long parentId;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.authorId = comment.getAuthor().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.editedAt = comment.getEditedAt();
        if (comment.getParent() == null) {
            this.parentId = null;
        } else {
            this.parentId = comment.getParent().getId();
        }
    }
} 