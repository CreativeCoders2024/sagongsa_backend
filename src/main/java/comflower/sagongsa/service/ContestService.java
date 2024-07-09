package comflower.sagongsa.service;


import comflower.sagongsa.dto.CreateContestDTO;
import comflower.sagongsa.dto.ListContestDTO;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.dto.EditContestDTO;
import comflower.sagongsa.dto.CheckContestDTO;
import org.springframework.transaction.annotation.Transactional; //readOnly = true를 쓰기 위함.
import comflower.sagongsa.entity.User;
import comflower.sagongsa.repository.ContestRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContestService {

    private final ContestRepository contestRepository;

    @Transactional
    //생성
    public void createContest(CreateContestDTO createContestDTO) {
        Contest createcon = Contest.builder()

                .userId(createContestDTO.getUserId())
                .title(createContestDTO.getTitle())
                .img(createContestDTO.getImg())
                .prize(createContestDTO.getPrize())
                //stattedAt, endedAt, link, field
                .startedAt(createContestDTO.getStartedAt())
                .endedAt(createContestDTO.getEndedAt())
                .link(createContestDTO.getLink())
                .field(createContestDTO.getField())
                .build();
        contestRepository.save(createcon);
    }
    //조회
    @Transactional(readOnly = true)
        public CheckContestDTO getContestById(Long contestId) {
            Contest contest = contestRepository.findById(contestId)
                    .orElseThrow(() -> new IllegalStateException("Contest with id : " + contestId + " not found"));

            return CheckContestDTO.builder()
                    .contestId(contest.getContestId())
                    .userId(contest.getUserId())
                    .title(contest.getTitle())
                    .img(contest.getImg())
                    .prize(contest.getPrize())
                    .startedAt(contest.getStartedAt())
                    .endedAt(contest.getEndedAt())
                    .link(contest.getLink())
                    .field(contest.getField())
                    .build();


    }
    //수정
   @Transactional
           public void editContest(EditContestDTO editContestDTO){
   Contest editContest = contestRepository.findByContestId(editContestDTO.getUserId()).orElseThrow(() ->
           new IllegalStateException("User with id : " + editContestDTO.getUserId() + " not found"));
    editContest.update(editContestDTO.getTitle(), editContestDTO.getImg(), editContestDTO.getPrize(), editContestDTO.getStartedAt(), editContestDTO.getEndedAt(), editContestDTO.getLink(), editContestDTO.getField());
        contestRepository.save(editContest);
    }
    //삭제
    @Transactional
    public void deleteContest(Long contestId) {
        Contest deletcontest = contestRepository.findById(contestId)
                .orElseThrow(() -> new IllegalStateException("Contest with id : " + contestId + " not found"));
        contestRepository.delete(deletcontest);
    }
     //공모전 목록
     @Transactional(readOnly = true)
     public List<ListContestDTO> getAllContests() {
         return contestRepository.findAll().stream()
                 .map(contest -> ListContestDTO.builder()
                         .contestId(contest.getContestId())
                         .startedAt(contest.getStartedAt())
                         .endedAt(contest.getEndedAt())
                         .build())
                 .collect(Collectors.toList());
     }
}
