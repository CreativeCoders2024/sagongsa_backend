package comflower.sagongsa.controller;
import comflower.sagongsa.dto.CheckContestDTO;
import comflower.sagongsa.dto.CreateContestDTO;
import comflower.sagongsa.dto.EditContestDTO;
import comflower.sagongsa.dto.ListContestDTO;
import comflower.sagongsa.repository.ContestRepository;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.ContestService;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List; //List

@RestController
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;
    private final ContestRepository contestRepository;

    //공모전 생성
    @PostMapping("/contests")
    public String createContest(@RequestBody CreateContestDTO createContestDTO) {
        contestService.createContest(createContestDTO);
        return "Success Signup : " + createContestDTO.getUserId() + " return";
    }

    //공모전 조회
    @GetMapping("/check/{contestId}")
    public CheckContestDTO getContest(@PathVariable Long contestId) {
        return contestService.getContestById(contestId);
    }
    //공모전 수정
    @PutMapping("/edit")
    public String editContest(@RequestBody EditContestDTO editContestDTO) {
    contestService.editContest(editContestDTO);
        return "Success Edit User : " + editContestDTO.getUserId() + " return";
    }
    // 공모전 삭제
    @DeleteMapping("/delete/{contestId}")
    public String deleteContest(@PathVariable Long contestId) {
        contestService.deleteContest(contestId);
        return "Success Delete Contest with id: " + contestId;
    }
    // 공모전 목록
    @GetMapping("/list")
    public List<ListContestDTO> getAllContests() {
        return contestService.getAllContests();
    }
}
