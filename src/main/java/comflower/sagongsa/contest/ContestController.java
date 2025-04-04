package comflower.sagongsa.contest;

import comflower.sagongsa.common.exception.InvalidFormBodyException;
import comflower.sagongsa.contest.request.CreateContestRequest;
import comflower.sagongsa.contest.request.EditContestRequest;
import comflower.sagongsa.common.request.RequestValidator;
import comflower.sagongsa.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import comflower.sagongsa.contest.response.ContestResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "contest")
public class ContestController {
    private final ContestService contestService;
    private final RequestValidator requestValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(requestValidator);
    }

    @GetMapping("/contests")
    public List<ContestResponse> getContests() {
        return contestService.getAllContests().stream()
                .map(ContestResponse::new)
                .toList();
    }

    @PostMapping("/contests")
    @SecurityRequirement(name = "bearerAuth")
    public ContestResponse createContest(
            @AuthenticationPrincipal User user,
            @Validated @RequestBody CreateContestRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
        }

        return new ContestResponse(contestService.createContest(user.getId(), request));
    }

    @GetMapping("/contests/{contestId}")
    public ContestResponse getContest(@PathVariable Long contestId) {
        return new ContestResponse(contestService.getContest(contestId));
    }

    @PutMapping("/contests/{contestId}")
    @SecurityRequirement(name = "bearerAuth")
    public ContestResponse editContest(
            @PathVariable Long contestId,
            @Validated @RequestBody EditContestRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
        }

        return new ContestResponse(contestService.editContest(contestId, request));
    }

    @DeleteMapping("/contests/{contestId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deleteContest(@PathVariable Long contestId) {
        contestService.deleteContest(contestId);
    }
}
