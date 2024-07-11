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
    @GetMapping("/liposts")  //url이 api랑 다름
    public List<ListPostDTO> getAllPosts() {
        return postService.getAllPosts();
    }
    //게시글 상세조회
    @GetMapping("/posts/{postId}")
    public CheckPostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }


    // 게시글 수정
    @PutMapping("/editPost")
    public String editPost(@RequestBody EditPostDTO editPostDTO) {
        postService.editPost(editPostDTO);
        return "Success Edit Post : " + editPostDTO.getUserId() + " return";
    }
    //게시글 삭제
    @DeleteMapping("/posts/delete/{postId}")  //url이 api랑 다름 -> api를 수정해야 되는거야 얘를 수정해야 되는거야 ㅠ
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "Success Delete Post: " + postId;
    }

}
