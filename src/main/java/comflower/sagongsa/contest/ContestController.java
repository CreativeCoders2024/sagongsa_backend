package comflower.sagongsa.contest;

import comflower.sagongsa.user.User;
import comflower.sagongsa.common.exception.InvalidFormBodyException;
import comflower.sagongsa.contest.request.CreateContestRequest;
import comflower.sagongsa.contest.request.EditContestRequest;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Contest createContest(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateContestRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(new HashMap<>());
        }

        return contestService.createContest(user.getId(), request);
    }

    @GetMapping("/contests/{contestId}")
    public Contest getContest(@PathVariable Long contestId) {
        return contestService.getContest(contestId);
    }

    @PutMapping("/contests/{contestId}")
    @SecurityRequirement(name = "bearerAuth")
    public Contest editContest(@PathVariable Long contestId, @RequestBody @Valid EditContestRequest request) {
        return contestService.editContest(contestId, request);
    }

    @DeleteMapping("/contests/{contestId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deleteContest(@PathVariable Long contestId) {
        contestService.deleteContest(contestId);
    }
}
