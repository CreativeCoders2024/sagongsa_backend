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

    @PostMapping("/contests")
    public String createContest(@RequestBody CreateContestDTO createContestDTO) {
        // 입력 데이터 유효성 검사
        validateCreateContestDTO(createContestDTO);
        long userId = 1L; //유저

        // 공모전 생성 로직 실행
        contestService.createContest(createContestDTO);
        return "Success Signup : " + userId + " return"; //createContestDTO.getUserId() -> 임의로 userId
    }
    @GetMapping("/contests/{id}")
    public Contest getContest(@PathVariable("id") Long contestId) {
        return contestService.getContestById(contestId)
                .orElseThrow(() -> new ContestNotFoundException(contestId));
    }

    @PutMapping("/contests/{id}")
    public String editContest(@PathVariable("id") Long contestId, @RequestBody EditContestDTO editContestDTO) {
        // 1. 공모전 존재 여부 확인
        long userId = 1L; //유저
        Contest contest = contestService.getContestById(contestId)
                .orElseThrow(() -> new ContestEditNotFoundException(contestId));

        // 2. 입력 데이터 유효성 검사
        validateEditContestDTO(editContestDTO);

        // 3. 공모전 수정
        contestService.editContest(contestId, editContestDTO);

        return "Success Edit Contest : " + userId + " return"; //editContestDTO.getUserId() 임의로 userId
    }
    @DeleteMapping("/contests/{id}")
    public String deleteContest(@PathVariable("id") Long contestId) {
        // 1. 공모전 존재 여부 확인
        contestService.getContestById(contestId)
                .orElseThrow(() -> new ContestDeleteFailedException(contestId));

        // 2. 공모전 삭제
        contestService.deleteContest(contestId);

        return "Success Delete Contest with id: " + contestId;
    }

    @GetMapping("/contests")
    public List<Contest> getAllContests() {
        List<Contest> contests = contestService.getAllContests();
        return contests;

    }
    // 유효성 검사 메서드 추가
    private void validateCreateContestDTO(CreateContestDTO createContestDTO) {

        if (createContestDTO.getTitle() == null || createContestDTO.getTitle().isEmpty()) {
            throw new InvalidContestDataException("Title is required");
        }
        if (createContestDTO.getStartedAt() == null) {
            throw new InvalidContestDataException("Start date is required");
        }
        if (createContestDTO.getEndedAt() == null) {
            throw new InvalidContestDataException("End date is required");
        }
//        if (createContestDTO.getUserId() == null) {
//            throw new InvalidContestDataException("User ID is required");
//        }
    }
    private void validateEditContestDTO(EditContestDTO editContestDTO) {
        if (editContestDTO.getTitle() == null || editContestDTO.getTitle().isEmpty()) {
            throw new InvalidContestEditDataException("Title is required");
        }
        if (editContestDTO.getPrize() == null || editContestDTO.getPrize().isEmpty()) {
            throw new InvalidContestEditDataException("Prize is required");
        }
        // 추가적인 유효성 검사가 필요한 경우 여기에 추가
    }


    @ExceptionHandler(InvalidContestDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidContestDataException(InvalidContestDataException e) {
        return ErrorResponse.entity(ErrorType.INVALID_CONTEST_DATA);
    }
    @ExceptionHandler(ContestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContestNotFoundException(ContestNotFoundException e) {
        return ErrorResponse.entity(ErrorType.CONTEST_NOT_FOUND, e.getContestId());
    }
    @ExceptionHandler(ContestEditNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContestEditNotFoundException(ContestEditNotFoundException e) {
        return ErrorResponse.entity(ErrorType.CONTEST_Edit_NOT_FOUND, e.getContestId());
    }
    @ExceptionHandler(InvalidContestEditDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidContestEditDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_CONTEST_Edit_DATA);
    }
    @ExceptionHandler(ContestDeleteFailedException.class)
    public ResponseEntity<ErrorResponse> handleContestDeleteFailedException(ContestDeleteFailedException e) {
        return ErrorResponse.entity(ErrorType.CONTEST_DELETE_FAILED, e.getContestId());
    }
}
