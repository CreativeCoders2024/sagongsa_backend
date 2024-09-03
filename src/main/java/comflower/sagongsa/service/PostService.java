package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreatePostDTO;
import comflower.sagongsa.dto.request.EditPostDTO;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.PostNotFoundException;
import comflower.sagongsa.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Transactional
    public Post createPost(long userId, CreatePostDTO createPostDTO) {
//        throw new UnsupportedOperationException("승희님 일해요");
        Post post = Post.builder()
                .userId(userId)
                .contestId(createPostDTO.getContestId())
                .title(createPostDTO.getTitle())
                .content(createPostDTO.getContent())
                .max(createPostDTO.getMax())
                .ppl(createPostDTO.getPpl())
                .desiredField(createPostDTO.getDesired_field())
                .createdAt(LocalDateTime.now())
                .endedAt(createPostDTO.getEndedAt())
                .build();
        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public Post editPost(Post post, EditPostDTO editPostDTO) {
        post.setTitle(editPostDTO.getTitle());
        post.setContent(editPostDTO.getContent());
        post.setPpl(editPostDTO.getPpl());
        post.setMax(editPostDTO.getMax());
        post.setDesiredField(editPostDTO.getDesiredField());
        post.setEndedAt(editPostDTO.getEndedAt());
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Post post) {
        postRepository.delete(post);
    }
}
