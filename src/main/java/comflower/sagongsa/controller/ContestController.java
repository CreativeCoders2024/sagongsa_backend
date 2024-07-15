package comflower.sagongsa.controller;
import comflower.sagongsa.dto.CreateContestDTO;
import comflower.sagongsa.dto.EditContestDTO;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.repository.ContestRepository;
import comflower.sagongsa.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List; //List

@RestController
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;

    //공모전 생성
    @PostMapping("/contests")
    public String createContest(@RequestBody CreateContestDTO createContestDTO) {
        contestService.createContest(createContestDTO);
        return "Success Signup : " + createContestDTO.getUserId() + " return";
    }

    //공모전 조회
    @GetMapping("/contests/{id}")
    public Contest getContest(@PathVariable("id") Long contestId) {
        return contestService.getContestById(contestId);
    }

    //공모전 수정
    @PutMapping("/contests/{id}")
    public String editContest(
            @RequestBody EditContestDTO editContestDTO,
            @PathVariable("id") Long contestId
    ) {
        contestService.editContest(contestId, editContestDTO);
        return "Success Edit User : " + editContestDTO.getUserId() + " return";
    }

    // 공모전 삭제
    @DeleteMapping("/contests/{id}")
    public String deleteContest(@PathVariable("id") Long contestId) {
        contestService.deleteContest(contestId);
        return "Success Delete Contest with id: " + contestId;
    }

    // 공모전 목록
    @GetMapping("/contests")
    public List<Contest> getAllContests() {
        return contestService.getAllContests();
    }
}
