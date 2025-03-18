package comflower.sagongsa.comment.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import comflower.sagongsa.comment.Comment;
import comflower.sagongsa.user.response.UserResponse;

public record UserCommentResponse(@JsonUnwrapped Comment comment, UserResponse author) {
}
