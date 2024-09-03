package comflower.sagongsa.error;

import lombok.Getter;

@Getter
public class ContestNotFoundException extends RuntimeException {
    private final Long contestId;

    public ContestNotFoundException(Long contestId) {
        super("Contest not found with id: " + contestId);
        this.contestId = contestId;
    }
}
