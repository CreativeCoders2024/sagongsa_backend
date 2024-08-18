package comflower.sagongsa.error;

public class ContestDeleteFailedException extends RuntimeException {
    private final Long contestId;

    public ContestDeleteFailedException(Long contestId) {
        super("Failed to delete contest with id: " + contestId);
        this.contestId = contestId;
    }

    public Long getContestId() {
        return contestId;
    }
}
