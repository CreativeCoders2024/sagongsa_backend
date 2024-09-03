package comflower.sagongsa.error;

import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException {
    private final Long postId;

    public PostNotFoundException(Long postId) {
        super("Post not found with id: " + postId);
        this.postId = postId;
    }

    public String getMessage() {
        return "Post not found with id: " + postId;
    }
}
