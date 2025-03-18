package comflower.sagongsa.comment.projection;

import comflower.sagongsa.comment.Comment;
import comflower.sagongsa.user.User;

public interface UserCommentProjection {
    Comment getComment();
    User getAuthor();
}
