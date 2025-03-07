package comflower.sagongsa.response;

import comflower.sagongsa.entity.User;

public record CommentWithUser(
        long id,
        long postId,
        long authorId,
        String content,
        long createdAt,
        Long editedAt,
        Long parentId,
        User user
) {
}
