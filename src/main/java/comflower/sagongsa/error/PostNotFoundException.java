package comflower.sagongsa.error;

import lombok.Getter;

@Getter
public class PostNotFoundException extends IllegalArgumentException {
    private final Long postId;

    public PostNotFoundException(Long postId) {
        super("Post not found with id: " + postId);
        this.postId = postId;
    }
}
