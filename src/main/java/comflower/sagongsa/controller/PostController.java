package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreatePostDTO;
import comflower.sagongsa.dto.request.EditPostDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.ErrorType;
import comflower.sagongsa.error.InvalidPostDataException;
import comflower.sagongsa.error.UnauthorizedException;
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
import java.util.Objects;

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
            @RequestBody @Valid CreatePostDTO createPostDTO, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidPostDataException();
        }

        return postService.createPost(user.getId(), createPostDTO);
    }

    @PutMapping("/posts/{postId}")
    @SecurityRequirement(name = "bearerAuth")
    public Post editPost(@PathVariable Long postId, @RequestBody @Valid EditPostDTO editPostDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidPostDataException();
        }

        Post post = postService.getPost(postId);

        if (!Objects.equals(post.getAuthorId(), editPostDTO.getAuthorId())) {
            throw new UnauthorizedException();
        }

        return postService.editPost(post, editPostDTO);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deletePost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        postService.deletePost(post);
    }

    @ExceptionHandler(InvalidPostDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPostDataException() {
        return ErrorResponse.entity(ErrorType.INVALID_POST_DATA);
    }
}
