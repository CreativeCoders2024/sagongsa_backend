package comflower.sagongsa.comment.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import comflower.sagongsa.user.response.UserResponse;

public record UserCommentResponse(@JsonUnwrapped CommentResponse comment, UserResponse author) {
}
