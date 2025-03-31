package comflower.sagongsa.comment;

import comflower.sagongsa.comment.request.CreateCommentRequest;
import comflower.sagongsa.comment.request.EditCommentRequest;
import comflower.sagongsa.comment.response.UserCommentResponse;
import comflower.sagongsa.comment.response.CommentResponse;
import comflower.sagongsa.common.exception.InvalidFormBodyException;
import comflower.sagongsa.common.request.RequestValidator;
import comflower.sagongsa.user.User;
import comflower.sagongsa.user.response.UserResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "comment")
public class CommentController {
    private final CommentService commentService;
    private final RequestValidator requestValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(requestValidator);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<UserCommentResponse> getCommentsByPostId(@PathVariable Long postId) {
        var comments = commentService.getCommentsByPost(postId);
        return comments.stream()
                .map(c -> new UserCommentResponse(new CommentResponse(c.getComment()), new UserResponse(c.getAuthor())))
                .toList();
    }

    @PostMapping("/posts/{postId}/comments")
    @SecurityRequirement(name = "bearerAuth")
    public CommentResponse createComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId,
            @Validated @RequestBody CreateCommentRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
        }

        return new CommentResponse(commentService.createComment(user.getId(), postId, request));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public CommentResponse editComment(
            @PathVariable Long postId, @PathVariable Long commentId,
            @Validated @RequestBody EditCommentRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
        }

        return new CommentResponse(commentService.editComment(postId, commentId, request));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
    }
}

