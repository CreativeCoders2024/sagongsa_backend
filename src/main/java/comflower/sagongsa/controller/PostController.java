package comflower.sagongsa.controller;

import comflower.sagongsa.entity.Post;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.ErrorType;
import comflower.sagongsa.error.InvalidPostDataException;
import comflower.sagongsa.request.CreatePostRequest;
import comflower.sagongsa.request.EditPostRequest;
import comflower.sagongsa.response.ErrorResponse;
import comflower.sagongsa.service.PostService;
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
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/posts/{postId}")
    public Post getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PostMapping("/posts")
    @SecurityRequirement(name = "bearerAuth")
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
    public Post editPost(@PathVariable Long postId, @RequestBody @Valid EditPostRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidPostDataException();
        }

        return postService.editPost(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    @ExceptionHandler(InvalidPostDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPostDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_POST_DATA);
    }
}
