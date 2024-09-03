package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.CreatePostDTO;
import comflower.sagongsa.dto.request.EditPostDTO;

import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;

import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.*;
import comflower.sagongsa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public Post createPost(@RequestBody CreatePostDTO createPostDTO) {
        validateCreatePostDTO(createPostDTO);
        return postService.createPost(1L, createPostDTO);
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable("id") Long postId) {
        return postService.getPost(postId);
    }

//    @PutMapping("/posts/{id}")
//    public String editPost(@PathVariable("id") Long postId, @RequestBody EditPostDTO editPostDTO ) {
//        postService.editPost(postId, editPostDTO);
//        return "Success Edit Post : " + editPostDTO.getUserId() + " return";
//    }

//    // 3. 권한 검사 = 자기 게시물도 아닌데 수정 하는 거 방지
//    if (!userHasPermission(post, editPostDTO.getUserId())) {
//        throw new UnauthorizedAccessException("You do not have permission to edit this post.");
//    }

    @PutMapping("/posts/{id}")
    public Post editPost(@PathVariable("id") Long postId, @RequestBody EditPostDTO editPostDTO) {
        Post post = postService.getPost(postId);

        validateEditPostDTO(editPostDTO);

        if (!userHasPermission(post, editPostDTO.getUserId())) {
            throw new UnauthorizedAccessException();
        }

        return postService.editPost(post, editPostDTO);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable("id") Long postId) {
        Post post = postService.getPost(postId);
        postService.deletePost(post);
    }

//    @DeleteMapping("/posts/{id}")
//    public String deletePost(@PathVariable("id") Long postId) {
//        // 1. 게시글 존재 여부 확인
//        Post post = postService.getPostById(postId)
//                .orElseThrow(() -> new PostNotFoundException(postId));
//         // 얘도 권한이 필요할 것 같은데 수정 부분에서 권한 느낌을 만들어 봤는데 이게 승희가
//        // 애초에 유저한테 자기것만 할 수 있게 권한을 주는건지 잘 모르겠어서 일단 보류
//        // 2. 게시글 삭제
//        postService.deletePost(postId);
//
//        return "Success: Post deleted successfully";
//    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        return ErrorResponse.entity(ErrorType.POST_NOT_FOUND, e.getPostId());
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

    private boolean userHasPermission(Post post, Long userId) {
        // 사용자가 해당 게시글을 수정할 권한이 있는지 확인하는 로직
        return post.getUserId().equals(userId); // 예시로 .
    }
}
