package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreateContestDTO;
import comflower.sagongsa.dto.request.EditContestDTO;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.error.ContestNotFoundException;
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
        return contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException(contestId));
    }

    public List<Contest> getAllContests() {
        return contestRepository.findAll();
    }

    public Contest createContest(Long userId, CreateContestDTO createContestDTO) {
        Contest contest = Contest.builder()
                .authorId(userId)
                .title(createContestDTO.getTitle())
                .thumbnail(createContestDTO.getThumbnail())
                .prize(createContestDTO.getPrize())
                .startedAt(createContestDTO.getStartedAt())
                .endedAt(createContestDTO.getEndedAt())
                .link(createContestDTO.getLink())
                .field(createContestDTO.getField())
                .build();
        return contestRepository.save(contest);
    }

    @Transactional
    public Contest editContest(Contest contest, EditContestDTO editContestDTO) {
        contest.setTitle(editContestDTO.getTitle());
        contest.setThumbnail(editContestDTO.getThumbnail());
        contest.setPrize(editContestDTO.getPrize());
        contest.setStartedAt(editContestDTO.getStartedAt());
        contest.setEndedAt(editContestDTO.getEndedAt());
        contest.setLink(editContestDTO.getLink());
        contest.setField(editContestDTO.getField());
        return contestRepository.save(contest);
    }

    public void deleteContest(Contest contest) {
        contestRepository.delete(contest);
    }
}
