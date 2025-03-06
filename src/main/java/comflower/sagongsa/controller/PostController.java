package comflower.sagongsa.controller;

import comflower.sagongsa.entity.Post;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.ErrorType;
import comflower.sagongsa.error.InvalidPostDataException;
import comflower.sagongsa.request.CreatePostRequest;
import comflower.sagongsa.request.EditPostRequest;
import comflower.sagongsa.response.ErrorResponse;
import comflower.sagongsa.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/posts")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 생성 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 게시글 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public Post createPost(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreatePostRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidPostDataException();
        }

        return postService.createPost(user.getId(), request);
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
    public Post editPost(@PathVariable Long postId, @RequestBody @Valid EditPostRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidPostDataException();
        }

        return postService.editPost(postId, request);
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
        postService.deletePost(postId);
    }

    @ExceptionHandler(InvalidPostDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPostDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_POST_DATA);
    }
}
