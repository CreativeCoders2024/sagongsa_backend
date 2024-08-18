package comflower.sagongsa.error;

public class ContestEditNotFoundException extends RuntimeException {
    private final Long contestId;

    public ContestEditNotFoundException(Long contestId) {
        super("Contest not found for modification with id: " + contestId);
        this.contestId = contestId;
    }

    public Long getContestId() {
        return contestId;
    }
}