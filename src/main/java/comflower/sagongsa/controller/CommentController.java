package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.CommentNotFoundException;
import comflower.sagongsa.error.ErrorType;
import comflower.sagongsa.error.InvalidCommentDataException;
import comflower.sagongsa.service.CommentService;
import comflower.sagongsa.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "comment")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return commentService.getCommentsByPost(post);
    }

    @PostMapping("/posts/{postId}/comments")
    @SecurityRequirement(name = "bearerAuth")
    public Comment createComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long postId,
            @RequestBody @Valid CreateCommentDTO createCommentDTO, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidCommentDataException();
        }

        Post post = postService.getPost(postId);

        Long parentId = createCommentDTO.getParentId();
        if (parentId != null && !commentService.isCommentPresentById(parentId)) {
            throw new CommentNotFoundException(parentId);
        }

        return commentService.createComment(user.getId(), post.getId(), createCommentDTO);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public Comment editComment(
            @PathVariable Long postId, @PathVariable Long commentId,
            @RequestBody @Valid EditCommentDTO editCommentDTO, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidCommentDataException();
        }

        Comment comment = commentService.getComment(commentId);
        return commentService.editComment(comment, editCommentDTO);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        Comment comment = commentService.getComment(commentId);
        commentService.deleteComment(comment);
    }

    @ExceptionHandler(InvalidCommentDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCommentDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_COMMENT_DATA);
    }
}

