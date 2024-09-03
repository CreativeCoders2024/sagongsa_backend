package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreateContestDTO;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.dto.request.EditContestDTO;
import comflower.sagongsa.error.ContestNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import comflower.sagongsa.repository.ContestRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContestService {
    private final ContestRepository contestRepository;

    public Contest getContest(Long contestId) {
        return contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException(contestId));
    }

    @Transactional
    public Contest createContest(long userId, CreateContestDTO createContestDTO) {
        //승희 일해요 부분
        Contest contest = Contest.builder()
                .userId(userId)
                .title(createContestDTO.getTitle())
                .img(createContestDTO.getImg())
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
        contest.setImg(editContestDTO.getImg());
        contest.setPrize(editContestDTO.getPrize());
        contest.setStartedAt(editContestDTO.getStartedAt());
        contest.setEndedAt(editContestDTO.getEndedAt());
        contest.setLink(editContestDTO.getLink());
        contest.setField(editContestDTO.getField());
        return contestRepository.save(contest);
    }

    @Transactional
    public void deleteContest(Contest contest) {
        contestRepository.delete(contest);
    }

    @Transactional(readOnly = true)
    public List<Contest> getAllContests() {
        return contestRepository.findAll();
    }
}
