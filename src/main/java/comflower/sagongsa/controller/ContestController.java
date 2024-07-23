package comflower.sagongsa.controller;

import comflower.sagongsa.dto.CreateContestDTO;
import comflower.sagongsa.dto.EditContestDTO;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;


    @PostMapping("/contests")
    public String createContest(@RequestBody CreateContestDTO createContestDTO) {
        throw new UnsupportedOperationException("승희님 일해요");
//        contestService.createContest(createContestDTO);
//        return "Success Signup : " + createContestDTO.getUserId() + " return";
    }

    @GetMapping("/contests/{id}")
    public Contest getContest(@PathVariable("id") Long contestId) {
        return contestService.getContestById(contestId);
    }

    @PutMapping("/contests/{id}")
    public String editContest(@PathVariable("id") Long contestId, @RequestBody EditContestDTO editContestDTO) {
        throw new UnsupportedOperationException("승희님 일해요");
//        contestService.editContest(contestId, editContestDTO);
//        return "Success Edit User : " + editContestDTO.getUserId() + " return";
    }

    @DeleteMapping("/contests/{id}")
    public String deleteContest(@PathVariable("id") Long contestId) {
        contestService.deleteContest(contestId);
        return "Success Delete Contest with id: " + contestId;
    }

    @GetMapping("/contests")
    public List<Contest> getAllContests() {
        return contestService.getAllContests();
    }
}
