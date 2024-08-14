package comflower.sagongsa.error;
public class PostNotFoundException extends RuntimeException {
    private final Long postId;

    public PostNotFoundException(Long postId) {
        super("Post not found with id: " + postId);
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
    public String getMessage() {
        return "Post not found with id: " + postId;
    }
}