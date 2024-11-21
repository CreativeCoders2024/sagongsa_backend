package comflower.sagongsa.controller;

import comflower.sagongsa.Placeholder;
import comflower.sagongsa.dto.request.CreateContestDTO;
import comflower.sagongsa.dto.request.EditContestDTO;
import comflower.sagongsa.dto.response.ErrorDataResponse;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.error.InvalidContestDataException;
import comflower.sagongsa.error.InvalidContestEditDataException;
import comflower.sagongsa.service.ContestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "contest")
public class ContestController {
    private final ContestService contestService;

    @GetMapping("/contests")
    @Operation(summary = "모든 콘테스트 조회", description = "모든 콘테스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 콘테스트 조회 성공",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Contest.class)))}),
    })
    public List<Contest> getAllContests() {
        return contestService.getAllContests();
    }

    @PostMapping("/contests")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "콘테스트 생성", description = "콘테스트를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콘테스트 생성 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contest.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 콘테스트 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Contest createContest(@RequestBody CreateContestDTO createContestDTO) {
        validateCreateContestDTO(createContestDTO);
        return contestService.createContest(Placeholder.SELF_USER_ID, createContestDTO);
    }

    @GetMapping("/contests/{contestId}")
    @Operation(summary = "콘테스트 조회", description = "콘테스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콘테스트 조회 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contest.class))}),
            @ApiResponse(responseCode = "404", description = "콘테스트 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Contest getContest(@PathVariable Long contestId) {
        return contestService.getContest(contestId);
    }

    @PutMapping("/contests/{contestId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "콘테스트 수정", description = "콘테스트를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "콘테스트 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contest.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 콘테스트 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Contest editContest(@PathVariable Long contestId, @RequestBody EditContestDTO editContestDTO) {
        Contest contest = contestService.getContest(contestId);
        validateEditContestDTO(editContestDTO);
        return contestService.editContest(contest, editContestDTO);
    }

    @DeleteMapping("/contests/{contestId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "콘테스트 삭제", description = "콘테스트를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "콘테스트 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "콘테스트 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDataResponse.class))}),
    })
    public void deleteContest(@PathVariable Long contestId) {
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

    @ExceptionHandler(InvalidContestEditDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidContestEditDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_CONTEST_EDIT_DATA);
    }
}
