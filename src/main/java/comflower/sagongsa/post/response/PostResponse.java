package comflower.sagongsa.post.response;

import comflower.sagongsa.post.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private final long id;
    private final long authorId;
    private final String title;
    private final String content;
    private final int memberCount;
    private final int maxMemberCount;
    private final int topic;
    private final long createdAt;
    private final long endedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.authorId = post.getAuthor().getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.memberCount = post.getMemberCount();
        this.maxMemberCount = post.getMaxMemberCount();
        this.topic = post.getTopic();
        this.createdAt = post.getCreatedAt();
        this.endedAt = post.getEndedAt();
    }
}
