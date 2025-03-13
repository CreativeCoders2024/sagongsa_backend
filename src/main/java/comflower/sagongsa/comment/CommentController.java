package comflower.sagongsa.comment;

import comflower.sagongsa.user.User;
import comflower.sagongsa.common.exception.InvalidFormBodyException;
import comflower.sagongsa.comment.projection.UserCommentProjection;
import comflower.sagongsa.comment.request.CreateCommentRequest;
import comflower.sagongsa.comment.request.EditCommentRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public List<UserCommentProjection> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    @SecurityRequirement(name = "bearerAuth")
    public Comment createComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId,
            @RequestBody @Valid CreateCommentRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(new HashMap<>());
        }

        return commentService.createComment(user.getId(), postId, request);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public Comment editComment(
            @PathVariable Long postId, @PathVariable Long commentId,
            @RequestBody @Valid EditCommentRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(new HashMap<>());
        }

        return commentService.editComment(postId, commentId, request);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
    }
}

