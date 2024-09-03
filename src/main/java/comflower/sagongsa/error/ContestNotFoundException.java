package comflower.sagongsa.error;

import lombok.Getter;

@Getter
public class ContestNotFoundException extends IllegalArgumentException {
    private final Long contestId;

    public ContestNotFoundException(Long contestId) {
        super("Contest not found with id: " + contestId);
        this.contestId = contestId;
    }
}
