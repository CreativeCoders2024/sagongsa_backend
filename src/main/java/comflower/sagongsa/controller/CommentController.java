package comflower.sagongsa.controller;

import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.exception.InvalidFormBodyException;
import comflower.sagongsa.request.CreateCommentRequest;
import comflower.sagongsa.request.EditCommentRequest;
import comflower.sagongsa.response.CommentWithUser;
import comflower.sagongsa.service.CommentService;
import comflower.sagongsa.validator.CreateCommentValidator;
import comflower.sagongsa.validator.EditCommentValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "comment")
public class CommentController {
    private final CommentService commentService;
    private final CreateCommentValidator createCommentValidator;
    private final EditCommentValidator editCommentValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createCommentValidator, editCommentValidator);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentWithUser> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsWithUserByPost(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    @SecurityRequirement(name = "bearerAuth")
    public Comment createComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId,
            @Validated @RequestBody CreateCommentRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
        }

        return commentService.createComment(user.getId(), postId, request);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public Comment editComment(
            @PathVariable Long postId, @PathVariable Long commentId,
            @Validated @RequestBody EditCommentRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
        }

        return commentService.editComment(postId, commentId, request);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
    }
}

