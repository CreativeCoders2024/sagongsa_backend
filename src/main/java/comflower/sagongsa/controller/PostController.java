package comflower.sagongsa.controller;

import comflower.sagongsa.Placeholder;
import comflower.sagongsa.dto.request.CreatePostDTO;
import comflower.sagongsa.dto.request.EditPostDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.InvalidPostDataException;
import comflower.sagongsa.error.UnauthorizedAccessException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Tag(name = "post")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    @Operation(summary = "모든 게시글 조회", description = "모든 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 게시글 조회 성공",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Post.class)))}),
    })
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @PostMapping("/posts")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 게시글 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Post createPost(@RequestBody CreatePostDTO createPostDTO) {
        validateCreatePostDTO(createPostDTO);
        return postService.createPost(Placeholder.SELF_USER_ID, createPostDTO);
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Post getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PutMapping("/posts/{postId}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 게시글 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
        public Post editPost(@PathVariable Long postId, @RequestBody EditPostDTO editPostDTO) {
        Post post = postService.getPost(postId);
        validateEditPostDTO(editPostDTO);

        if (!Objects.equals(post.getAuthorId(), editPostDTO.getAuthorId())) {
            throw new UnauthorizedAccessException();
        }

        return postService.editPost(post, editPostDTO);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public void deletePost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        postService.deletePost(post);
    }

    @ExceptionHandler(InvalidPostDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPostDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_POST_DATA);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return ErrorResponse.entity(ErrorType.UNAUTHORIZED_ACCESS, e.getMessage());
    }

    private void validateCreatePostDTO(CreatePostDTO createPostDTO) {
        if (createPostDTO.getTitle() == null || createPostDTO.getTitle().isEmpty()) {
            throw new InvalidPostDataException();
        }
        if (createPostDTO.getContent() == null || createPostDTO.getContent().isEmpty()) {
            throw new InvalidPostDataException();
        }
        if (createPostDTO.getContestId() == null) {
            throw new InvalidPostDataException();
        }
        // 일단 예시로 공모전과 제목이 널일 때만
    }

    private void validateEditPostDTO(EditPostDTO editPostDTO) {
        if (editPostDTO.getTitle() == null || editPostDTO.getTitle().isEmpty()) {
            throw new InvalidPostDataException();
        }
        if (editPostDTO.getContent() == null || editPostDTO.getContent().isEmpty()) {
            throw new InvalidPostDataException();
        }
    }
}
