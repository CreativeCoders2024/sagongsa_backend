package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.InvalidCommentDataException;
import comflower.sagongsa.error.PostNotFoundException;
import comflower.sagongsa.service.CommentService;
import comflower.sagongsa.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comment", description = "Comment API")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/posts/{postId}/comments")
    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 댓글 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
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
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 댓글 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Comment editComment(@PathVariable long postId, @PathVariable long commentId, @RequestBody EditCommentDTO editCommentDTO) {
        Comment comment = commentService.getComment(commentId);
        validateEditCommentDTO(editCommentDTO);
        return commentService.editComment(comment, editCommentDTO);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "404", description = "댓글 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public void deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        Comment comment = commentService.getComment(commentId);
        commentService.deleteComment(comment);
    }

    @GetMapping("/posts/{postId}/comments")
    @Operation(summary = "댓글 조회", description = "댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class)))}),
            @ApiResponse(responseCode = "404", description = "댓글 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
        public List<Comment> getCommentsByPostId(@PathVariable long postId) {
        Post post = postService.getPost(postId);
        return commentService.getCommentsByPostId(post);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        return ErrorResponse.entity(ErrorType.POST_NOT_FOUND, e.getPostId());
    }

    @ExceptionHandler(InvalidCommentDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCommentDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_COMMENT_DATA);
    }

    private void validateCreateCommentDTO(CreateCommentDTO createCommentDTO) {
        if (createCommentDTO.getContent() == null || createCommentDTO.getContent().isEmpty()) {
            throw new InvalidCommentDataException();
        }
    }

    private void validateEditCommentDTO(EditCommentDTO editCommentDTO) {
        if (editCommentDTO.getContent() == null || editCommentDTO.getContent().isEmpty()) {
            throw new InvalidCommentDataException();
        }
    }
}

