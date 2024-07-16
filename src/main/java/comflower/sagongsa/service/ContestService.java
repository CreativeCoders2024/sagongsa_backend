package comflower.sagongsa.service;

import comflower.sagongsa.dto.CreateContestDTO;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.dto.EditContestDTO;
import org.springframework.transaction.annotation.Transactional;
import comflower.sagongsa.repository.ContestRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContestService {
    private final ContestRepository contestRepository;

    @Transactional
    public void createContest(CreateContestDTO createContestDTO) {
        throw new UnsupportedOperationException("승희님 일해요");
//        Contest contest = Contest.builder()
//                .userId(0L)
//                .title(createContestDTO.getTitle())
//                .img(createContestDTO.getImg())
//                .prize(createContestDTO.getPrize())
//                .startedAt(createContestDTO.getStartedAt())
//                .endedAt(createContestDTO.getEndedAt())
//                .link(createContestDTO.getLink())
//                .field(createContestDTO.getField())
//                .build();
//        contestRepository.save(contest);
    }

    @Transactional(readOnly = true)
    public Contest getContestById(Long contestId) {
        return contestRepository.findById(contestId).orElseThrow(() -> new IllegalStateException("Contest with id : " + contestId + " not found"));
    }

    @Transactional
    public void editContest(long contestId, EditContestDTO editContestDTO) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new IllegalStateException("Contest with id : " + contestId + " not found"));

        contest.setTitle(editContestDTO.getTitle());
        contest.setImg(editContestDTO.getImg());
        contest.setPrize(editContestDTO.getPrize());
        contest.setStartedAt(editContestDTO.getStartedAt());
        contest.setEndedAt(editContestDTO.getEndedAt());
        contest.setLink(editContestDTO.getLink());
        contest.setField(editContestDTO.getField());
    }

    @Transactional
    public void deleteContest(Long contestId) {
        Contest deletcontest = contestRepository.findById(contestId)
                .orElseThrow(() -> new IllegalStateException("Contest with id : " + contestId + " not found"));
        contestRepository.delete(deletcontest);
    }

     @Transactional(readOnly = true)
     public List<Contest> getAllContests() {
         return contestRepository.findAll();
     }
}
