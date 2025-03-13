package comflower.sagongsa.projection;

import comflower.sagongsa.entity.User;

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
