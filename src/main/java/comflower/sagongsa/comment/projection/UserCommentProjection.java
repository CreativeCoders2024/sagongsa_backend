package comflower.sagongsa.comment.projection;

import comflower.sagongsa.user.User;

public interface UserCommentProjection {
    long getId();
    long getPostId();
    long getAuthorId();
    String getContent();
    long getCreatedAt();
    Long getEditedAt();
    Long getParentId();

    User getAuthor();
}
