package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.error.CommentNotFoundException;
import comflower.sagongsa.error.InvalidCommentDataException;
import comflower.sagongsa.error.PostNotFoundException;
import comflower.sagongsa.service.CommentService;
import comflower.sagongsa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable long postId, @RequestBody CreateCommentDTO createCommentDTO) {
        // 게시글 존재 여부 확인
        if (!postService.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }

        // 댓글 데이터 유효성 검증
        validateCreateCommentDTO(createCommentDTO);

        // 부모 댓글이 존재하는지 확인 (대댓글인 경우)
        if (createCommentDTO.getParentId() != null) {
            if (!commentService.existsById(createCommentDTO.getParentId())) {
                throw new CommentNotFoundException(createCommentDTO.getParentId());
            }
        }

        // 댓글 생성 로직
        commentService.createComment(postId, createCommentDTO);
        return "Comment created successfully";
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public String editComment(@PathVariable long postId, @PathVariable long commentId, @RequestBody EditCommentDTO editCommentDTO) {
        // 1. 댓글이 존재하는지 확인
        Comment comment = commentService.getCommentById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        // 2. 댓글 내용 유효성 검증
        validateEditCommentDTO(editCommentDTO);

        // 3. 댓글 수정 로직
        commentService.editComment(postId, commentId, editCommentDTO);

        return "Comment edited successfully";
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable long postId, @PathVariable long commentId){
        commentService.deleteComment(commentId);
        return "Success Delete Contest with id: " + commentId;
    }
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        return ErrorResponse.entity(ErrorType.POST_NOT_FOUND, e.getPostId());
    }
    @GetMapping("/posts/{postId}/comments/")
    public List<Comment> getComment(@PathVariable long postId) {
       return commentService.getComment(postId);
    }
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e) {
        return ErrorResponse.entity(ErrorType.COMMENT_NOT_FOUND, e.getCommentId());
    }

    @ExceptionHandler(InvalidCommentDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCommentDataException(InvalidCommentDataException e) {
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

