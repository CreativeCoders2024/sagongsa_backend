package comflower.sagongsa.controller;

import comflower.sagongsa.Placeholder;
import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.CommentNotFoundException;
import comflower.sagongsa.error.InvalidCommentDataException;
import comflower.sagongsa.service.CommentService;
import comflower.sagongsa.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "comment")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/posts/{postId}/comments")
    @Operation(summary = "댓글 조회", description = "댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class)))}),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return commentService.getCommentsByPost(post);
    }

    @PostMapping("/posts/{postId}/comments")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "댓글 생성", description = "댓글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 댓글 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Comment createComment(@PathVariable Long postId, @RequestBody CreateCommentDTO createCommentDTO) {
        Post post = postService.getPost(postId);
        validateCreateCommentDTO(createCommentDTO);

        Long parentId = createCommentDTO.getParentId();
        if (parentId != null && !commentService.isCommentPresentById(parentId)) {
            throw new CommentNotFoundException(parentId);
        }

        return commentService.createComment(Placeholder.SELF_USER_ID, post.getId(), createCommentDTO);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 댓글 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Comment editComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody EditCommentDTO editCommentDTO) {
        Comment comment = commentService.getComment(commentId);
        validateEditCommentDTO(editCommentDTO);
        return commentService.editComment(comment, editCommentDTO);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "404", description = "댓글 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        Comment comment = commentService.getComment(commentId);
        commentService.deleteComment(comment);
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

