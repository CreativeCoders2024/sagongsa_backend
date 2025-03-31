package comflower.sagongsa.contest.response;

import comflower.sagongsa.contest.Contest;
import lombok.Getter;

@Getter
public class ContestResponse {
    private final long id;
    private final long authorId;
    private final String title;
    private final String thumbnail;
    private final String prize;
    private final long startedAt;
    private final long endedAt;
    private final String link;
    private final int topic;

    public ContestResponse(Contest contest) {
        this.id = contest.getId();
        this.authorId = contest.getAuthor().getId();
        this.title = contest.getTitle();
        this.thumbnail = contest.getThumbnail();
        this.prize = contest.getPrize();
        this.startedAt = contest.getStartedAt();
        this.endedAt = contest.getEndedAt();
        this.link = contest.getLink();
        this.topic = contest.getTopic();
    }
} 