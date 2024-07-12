package comflower.sagongsa.service;


import comflower.sagongsa.dto.CheckPostDTO;
import comflower.sagongsa.dto.CreatePostDTO;
import comflower.sagongsa.dto.EditPostDTO;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //게시글 생성
    @Transactional
    public void createPost(CreatePostDTO createPostDTO) {
        Post post = Post.builder()
                .userId(createPostDTO.getUserId())
                .contestId(createPostDTO.getContestId())
                .title(createPostDTO.getTitle())
                .content(createPostDTO.getContent())
                .max(createPostDTO.getMax())
                .ppl(createPostDTO.getPpl())
                .desiredField(createPostDTO.getDesired_field())
                .createdAt(createPostDTO.getCreatedAt())
                .endedAt(createPostDTO.getEndedAt())
                .build();
        postRepository.save(post);
    }
    //게시글 목록
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    // 게시글 상세조회
    @Transactional(readOnly = true)
    public CheckPostDTO getPostById(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));

        return CheckPostDTO.builder()
                .postId(post.getPostId())
                .userId(post.getUserId())
                .contestId(post.getContestId())
                .title(post.getTitle())
                .content(post.getContent())
                .max(post.getMax())
                .ppl(post.getPpl())
                .desiredField(post.getDesiredField())
                .createdAt(post.getCreatedAt())
                .endedAt(post.getEndedAt())
                .build();
    }

    //게시글 수정
    @Transactional
    public void editPost(EditPostDTO editPostDTO) {
        Post post = postRepository.findById(editPostDTO.getPostId())
                .orElseThrow(() -> new IllegalStateException("Post with id : " + editPostDTO.getPostId() + " not found"));

        if (editPostDTO.getTitle() != null) {
            post.setTitle(editPostDTO.getTitle());
        }
        if (editPostDTO.getContent() != null) {
            post.setContent(editPostDTO.getContent());
        }
        if (editPostDTO.getMax() != null) {
            post.setMax(editPostDTO.getMax());
        }
        if (editPostDTO.getPpl() != null) {
            post.setPpl(editPostDTO.getPpl());
        }
        if (editPostDTO.getDesiredField() != null) {
            post.setDesiredField(editPostDTO.getDesiredField());
        }
        if (editPostDTO.getCreatedAt() != null) {
            post.setCreatedAt(editPostDTO.getCreatedAt());
        }
        if (editPostDTO.getEndedAt() != null) {
            post.setEndedAt(editPostDTO.getEndedAt());
        }
    }







    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));
        postRepository.delete(post);
    }
}
