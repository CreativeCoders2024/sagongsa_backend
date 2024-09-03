package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;

import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.CommentNotFoundException;
import comflower.sagongsa.error.InvalidCommentDataException;
import comflower.sagongsa.error.PostNotFoundException;
import comflower.sagongsa.service.CommentService;
import comflower.sagongsa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/posts/{postId}/comments")
    public Comment createComment(@PathVariable long postId, @RequestBody CreateCommentDTO createCommentDTO) {
        Post post = postService.getPost(postId);
        validateCreateCommentDTO(createCommentDTO);

        // 부모 댓글이 존재하는지 확인 (대댓글인 경우)
        if (createCommentDTO.getParentId() != null) {
            commentService.getComment(createCommentDTO.getParentId());
        }

        return commentService.createComment(post.getPostId(), createCommentDTO);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public Comment editComment(@PathVariable long postId, @PathVariable long commentId, @RequestBody EditCommentDTO editCommentDTO) {
        Comment comment = commentService.getComment(commentId);
        validateEditCommentDTO(editCommentDTO);
        return commentService.editComment(comment, editCommentDTO);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        Comment comment = commentService.getComment(commentId);
        commentService.deleteComment(comment);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable long postId) {
        Post post = postService.getPost(postId);
        return commentService.getCommentsByPostId(post);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        return ErrorResponse.entity(ErrorType.POST_NOT_FOUND, e.getPostId());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e) {
        return ErrorResponse.entity(ErrorType.COMMENT_NOT_FOUND, e.getCommentId());
    }

    @ExceptionHandler(InvalidCommentDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCommentDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_COMMENT_DATA);
    }

    private void validateCreateCommentDTO(CreateCommentDTO createCommentDTO) {
        if (createCommentDTO.getContent() == null || createCommentDTO.getContent().isEmpty()) {
            throw new InvalidCommentDataException("Comment content is required");
        }
    }

    private void validateEditCommentDTO(EditCommentDTO editCommentDTO) {
        if (editCommentDTO.getContent() == null || editCommentDTO.getContent().isEmpty()) {
            throw new InvalidCommentDataException("Comment content is required");
        }
    }
}

