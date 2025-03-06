package comflower.sagongsa.service;

import comflower.sagongsa.request.CreatePostRequest;
import comflower.sagongsa.request.EditPostRequest;
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

    public Post createPost(Long authorId, CreatePostRequest request) {
        Post post = Post.builder()
                .authorId(authorId)
                .contestId(request.getContestId())
                .title(request.getTitle())
                .content(request.getContent())
                .maxMemberCount(request.getMaxMemberCount())
                .memberCount(request.getMemberCount())
                .desiredField(request.getDesiredField())
                .createdAt(LocalDateTime.now())
                .endedAt(request.getEndedAt())
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public Post editPost(Post post, EditPostRequest request) {
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setMemberCount(request.getMemberCount());
        post.setMaxMemberCount(request.getMaxMemberCount());
        post.setDesiredField(request.getDesiredField());
        post.setEndedAt(request.getEndedAt());
        return postRepository.save(post);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }
}
