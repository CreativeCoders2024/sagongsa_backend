package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreateContestDTO;
import comflower.sagongsa.dto.request.EditContestDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.error.*;
import comflower.sagongsa.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;

    @GetMapping("/contests")
    public List<Contest> getAllContests() {
        return contestService.getAllContests();
    }

    @PostMapping("/contests")
    public Contest createContest(@RequestBody CreateContestDTO createContestDTO) {
        validateCreateContestDTO(createContestDTO);
        long userId = 1L; //유저
        return contestService.createContest(userId, createContestDTO);
    }

    @GetMapping("/contests/{id}")
    public Contest getContest(@PathVariable("id") Long contestId) {
        return contestService.getContest(contestId);
    }

    @PutMapping("/contests/{id}")
    public Contest editContest(@PathVariable("id") Long contestId, @RequestBody EditContestDTO editContestDTO) {
        Contest contest = contestService.getContest(contestId);
        validateEditContestDTO(editContestDTO);
        return contestService.editContest(contest, editContestDTO);
    }

    @DeleteMapping("/contests/{id}")
    public void deleteContest(@PathVariable("id") Long contestId) {
        Contest contest = contestService.getContest(contestId);
        contestService.deleteContest(contest);
    }

    // 유효성 검사 메서드 추가
    private void validateCreateContestDTO(CreateContestDTO createContestDTO) {
        if (createContestDTO.getTitle() == null || createContestDTO.getTitle().isEmpty()) {
            throw new InvalidContestDataException();
        }
        if (createContestDTO.getStartedAt() == null) {
            throw new InvalidContestDataException();
        }
        if (createContestDTO.getEndedAt() == null) {
            throw new InvalidContestDataException();
        }
//        if (createContestDTO.getUserId() == null) {
//            throw new InvalidContestDataException("User ID is required");
//        }
    }

    private void validateEditContestDTO(EditContestDTO editContestDTO) {
        if (editContestDTO.getTitle() == null || editContestDTO.getTitle().isEmpty()) {
            throw new InvalidContestEditDataException();
        }
        if (editContestDTO.getPrize() == null || editContestDTO.getPrize().isEmpty()) {
            throw new InvalidContestEditDataException();
        }
        // 추가적인 유효성 검사가 필요한 경우 여기에 추가
    }

    @ExceptionHandler(InvalidContestDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidContestDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_CONTEST_DATA);
    }

    @ExceptionHandler(ContestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContestNotFoundException(ContestNotFoundException e) {
        return ErrorResponse.entity(ErrorType.CONTEST_NOT_FOUND, e.getContestId());
    }

    @ExceptionHandler(InvalidContestEditDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidContestEditDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_CONTEST_EDIT_DATA);
    }
}
