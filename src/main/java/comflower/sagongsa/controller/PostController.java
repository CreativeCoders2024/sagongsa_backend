package comflower.sagongsa.controller;

import comflower.sagongsa.dto.CreatePostDTO;
import comflower.sagongsa.dto.EditPostDTO;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public String createPost(@RequestBody CreatePostDTO createPostDTO) {
        postService.createPost(createPostDTO);
        return "Success: Post created with title - " + createPostDTO.getTitle();
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable("id") Long postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("/posts/{id}")
    public String editPost(@PathVariable("id") Long postId, @RequestBody EditPostDTO editPostDTO ) {
        postService.editPost(postId, editPostDTO);
        return "Success Edit Post : " + editPostDTO.getUserId() + " return";
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return "Success Delete Post: " + postId;
    }
}
