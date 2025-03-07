package comflower.sagongsa.request;

import lombok.Getter;

@Getter
public class CreatePostRequest {
    private String title;
    private String content;
    private int memberCount;
    private int maxMemberCount;
    private int topic;
    private long endedAt;
}
