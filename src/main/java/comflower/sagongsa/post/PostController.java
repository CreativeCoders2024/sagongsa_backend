package comflower.sagongsa.post;

import comflower.sagongsa.common.exception.InvalidFormBodyException;
import comflower.sagongsa.post.request.CreatePostRequest;
import comflower.sagongsa.post.request.EditPostRequest;
import comflower.sagongsa.common.request.RequestValidator;
import comflower.sagongsa.post.response.PostResponse;
import comflower.sagongsa.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "post")
public class PostController {
    private final PostService postService;
    private final RequestValidator requestValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(requestValidator);
    }

    @GetMapping("/posts")
    public List<PostResponse> getPosts() {
        return postService.getPosts().stream()
                .map(PostResponse::new)
                .toList();
    }

    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        return new PostResponse(postService.getPost(postId));
    }

    @PostMapping("/posts")
    @SecurityRequirement(name = "bearerAuth")
    public PostResponse createPost(
            @AuthenticationPrincipal User user,
            @Validated @RequestBody CreatePostRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
        }

        return new PostResponse(postService.createPost(user.getId(), request));
    }

    @PutMapping("/posts/{postId}")
    @SecurityRequirement(name = "bearerAuth")
    public PostResponse editPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User user,
            @Validated @RequestBody EditPostRequest request, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFormBodyException(bindingResult);
        }

        return new PostResponse(postService.editPost(postId, user, request));
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
