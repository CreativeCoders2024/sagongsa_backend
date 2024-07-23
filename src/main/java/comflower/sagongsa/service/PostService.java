package comflower.sagongsa.service;

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
    public Post getPostById(Long postId) {
        return postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id : " + postId + " not found"));
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
}
