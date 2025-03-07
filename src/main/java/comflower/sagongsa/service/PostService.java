package comflower.sagongsa.service;

import comflower.sagongsa.exception.UnauthorizedException;
import comflower.sagongsa.request.CreatePostRequest;
import comflower.sagongsa.request.EditPostRequest;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.exception.UnknownPostException;
import comflower.sagongsa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(UnknownPostException::new);
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
    public Post editPost(Long postId, EditPostRequest request) {
        Post post = getPost(postId);

        if (!Objects.equals(post.getAuthorId(), request.getAuthorId())) {
            throw new UnauthorizedException();
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setMemberCount(request.getMemberCount());
        post.setMaxMemberCount(request.getMaxMemberCount());
        post.setDesiredField(request.getDesiredField());
        post.setEndedAt(request.getEndedAt());
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        Post post = getPost(postId);
        postRepository.delete(post);
    }
}
