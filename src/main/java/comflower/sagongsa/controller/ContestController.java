package comflower.sagongsa.controller;

import comflower.sagongsa.entity.Contest;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.exception.InvalidFormBodyException;
import comflower.sagongsa.request.CreateContestRequest;
import comflower.sagongsa.request.EditContestRequest;
import comflower.sagongsa.service.ContestService;
import comflower.sagongsa.validator.CreateContestValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "contest")
public class ContestController {
    private final ContestService contestService;

    private final CreateContestValidator createContestValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createContestValidator);
    }

    @GetMapping("/contests")
    public List<Contest> getContests() {
        return contestService.getAllContests();
    }

    @PostMapping("/contests")
    @SecurityRequirement(name = "bearerAuth")
    public Contest createContest(
            @AuthenticationPrincipal User user,
            @Validated @RequestBody CreateContestRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
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
