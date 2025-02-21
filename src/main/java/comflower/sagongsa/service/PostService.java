package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreatePostDTO;
import comflower.sagongsa.dto.request.EditPostDTO;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.PostNotFoundException;
import comflower.sagongsa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    public Post createPost(Long authorId, CreatePostDTO createPostDTO) {
        Post post = Post.builder()
                .authorId(authorId)
                .contestId(createPostDTO.getContestId())
                .title(createPostDTO.getTitle())
                .content(createPostDTO.getContent())
                .maxMemberCount(createPostDTO.getMaxMemberCount())
                .memberCount(createPostDTO.getMemberCount())
                .desiredField(createPostDTO.getDesiredField())
                .createdAt(LocalDateTime.now())
                .endedAt(createPostDTO.getEndedAt())
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public Post editPost(Post post, EditPostDTO editPostDTO) {
        post.setTitle(editPostDTO.getTitle());
        post.setContent(editPostDTO.getContent());
        post.setMemberCount(editPostDTO.getMemberCount());
        post.setMaxMemberCount(editPostDTO.getMaxMemberCount());
        post.setDesiredField(editPostDTO.getDesiredField());
        post.setEndedAt(editPostDTO.getEndedAt());
        return postRepository.save(post);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }
}
