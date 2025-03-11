package comflower.sagongsa.service;

import comflower.sagongsa.request.CreateContestRequest;
import comflower.sagongsa.request.EditContestRequest;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.exception.UnknownContestException;
import comflower.sagongsa.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContestService {
    private final ContestRepository contestRepository;

    public Contest getContest(Long contestId) {
        return contestRepository.findById(contestId).orElseThrow(UnknownContestException::new);
    }

    public List<Contest> getAllContests() {
        return contestRepository.findAll();
    }

    public Contest createContest(Long userId, CreateContestRequest request) {
        Contest contest = Contest.builder()
                .authorId(userId)
                .title(request.getTitle())
                .thumbnail(request.getThumbnail())
                .prize(request.getPrize())
                .startedAt(request.getStartedAt())
                .endedAt(request.getEndedAt())
                .link(request.getLink())
                .topic(request.getTopic())
                .build();
        return contestRepository.save(contest);
    }

    @Transactional
    public Contest editContest(Long contestId, EditContestRequest request) {
        Contest contest = getContest(contestId);
        contest.setTitle(request.getTitle());
        contest.setThumbnail(request.getThumbnail());
        contest.setPrize(request.getPrize());
        contest.setStartedAt(request.getStartedAt());
        contest.setEndedAt(request.getEndedAt());
        contest.setLink(request.getLink());
        contest.setTopic(request.getField());
        return contestRepository.save(contest);
    }

    public void deleteContest(Long contestId) {
        Contest contest = getContest(contestId);
        contestRepository.delete(contest);
    }
}
