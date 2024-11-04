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

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/posts/{postId}/comments")
    public String createComment(@PathVariable long postId, @RequestBody CreateCommentDTO createCommentDTO, @RequestHeader("Authorization") String token) {
        // 게시글 존재 여부 확인
        if (!postService.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }

        // 댓글 데이터 유효성 검증
        validateCreateCommentDTO(createCommentDTO);

        // 부모 댓글이 존재하는지 확인 (대댓글인 경우)
        if (createCommentDTO.getParentId() != null && !commentService.existsById(createCommentDTO.getParentId())) {
            throw new CommentNotFoundException(createCommentDTO.getParentId());
        }

        // 댓글 생성 로직
        commentService.createComment(postId, createCommentDTO, token);
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
        commentService.editComment(commentId, editCommentDTO);

        return "Comment edited successfully";
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public String deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        // 1. 댓글이 존재하는지 확인
        Comment comment = commentService.getCommentById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        // 2. 댓글 삭제 로직
        commentService.deleteComment(commentId);
        return "Success: Deleted comment with id: " + commentId;
    }

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable long postId) {
        // 1. 게시글이 존재하는지 확인
        if (!postService.existsById(postId)) {
            throw new PostNotFoundException(postId);
        }
        // 2. 해당 게시글의 댓글 리스트 반환
        return commentService.getComments(postId);
    }

    // 예외 처리
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        return ErrorResponse.entity(ErrorType.POST_NOT_FOUND, e.getPostId());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e) {
        return ErrorResponse.entity(ErrorType.COMMENT_NOT_FOUND, e.getCommentId());
    }

    @ExceptionHandler(InvalidCommentDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCommentDataException(InvalidCommentDataException e) {
        return ErrorResponse.entity(ErrorType.INVALID_COMMENT_DATA);
    }

    // 유효성 검사 메서드
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
