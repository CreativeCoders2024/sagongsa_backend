package comflower.sagongsa.error;

public class ContestNotFoundException extends RuntimeException {
    private final Long contestId;

    public ContestNotFoundException(Long contestId) {
        super("Contest not found with id: " + contestId);
        this.contestId = contestId;
    }

    public Long getContestId() {
        return contestId;
    }
}
