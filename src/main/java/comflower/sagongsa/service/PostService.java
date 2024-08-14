package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreatePostDTO;
import comflower.sagongsa.dto.request.EditPostDTO;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void createPost(CreatePostDTO createPostDTO) {
        throw new UnsupportedOperationException("승희님 일해요");
//        Post post = Post.builder()
//                .userId(0L)
//                .contestId(createPostDTO.getContestId())
//                .title(createPostDTO.getTitle())
//                .content(createPostDTO.getContent())
//                .max(createPostDTO.getMax())
//                .ppl(createPostDTO.getPpl())
//                .desiredField(createPostDTO.getDesired_field())
//                .createdAt(LocalDateTime.now())
//                .endedAt(createPostDTO.getEndedAt())
//                .build();
//        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findByPostId(postId);
    }
    @Transactional
    public void editPost(long postId, EditPostDTO editPostDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));

        post.setTitle(editPostDTO.getTitle());
        post.setContent(editPostDTO.getContent());
        post.setPpl(editPostDTO.getPpl());
        post.setMax(editPostDTO.getMax());
        post.setDesiredField(editPostDTO.getDesiredField());
        post.setEndedAt(editPostDTO.getEndedAt());
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));
        postRepository.delete(post);
    }
    // 댓글 생성 게시글 존재하는지 메서드 추가
    public boolean existsById(Long postId) {
        return postRepository.existsById(postId);
    }
}
