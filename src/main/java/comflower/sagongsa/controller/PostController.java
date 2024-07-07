package comflower.sagongsa.controller;


import comflower.sagongsa.dto.CheckPostDTO;
import comflower.sagongsa.dto.CreatePostDTO;
import comflower.sagongsa.dto.EditPostDTO;
import comflower.sagongsa.dto.ListPostDTO;
import comflower.sagongsa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //게시글 생성
    @PostMapping("/posts")
    public String createPost(@RequestBody CreatePostDTO createPostDTO) {
        postService.createPost(createPostDTO);
        return "Success: Post created with title - " + createPostDTO.getTitle();
    }
    //게시글 목록
    @GetMapping("/liposts")
    public List<ListPostDTO> getAllPosts() {
        return postService.getAllPosts();
    }
    //게시글 상세조회
    @GetMapping("/posts/{postId}")
    public CheckPostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    //게시글 수정
    @PutMapping("/posts/{postId}")
    public String editPost(@PathVariable Long postId, @RequestBody EditPostDTO editPostDTO) {
        postService.editPost(postId, editPostDTO);
        return "Success Edit Post : " + postId;
    }
    //게시글 삭제
    @DeleteMapping("/posts/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "Success Delete Post: " + postId;
    }

}
