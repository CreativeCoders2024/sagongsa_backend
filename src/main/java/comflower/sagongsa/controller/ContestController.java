package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreateContestDTO;
import comflower.sagongsa.dto.request.EditContestDTO;
import comflower.sagongsa.dto.response.ErrorDataResponse;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.ErrorType;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
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
    public List<Contest> getContests() {
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
    public Contest createContest(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateContestDTO createContestDTO, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidContestDataException();
        }

        return contestService.createContest(user.getId(), createContestDTO);
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
    public Contest editContest(@PathVariable Long contestId, @RequestBody @Valid EditContestDTO editContestDTO) {
        Contest contest = contestService.getContest(contestId);
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

    @ExceptionHandler(InvalidContestDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidContestDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_CONTEST_DATA);
    }

    @ExceptionHandler(InvalidContestEditDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidContestEditDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_CONTEST_EDIT_DATA);
    }
}
