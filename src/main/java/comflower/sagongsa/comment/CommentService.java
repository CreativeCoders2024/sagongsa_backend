package comflower.sagongsa.comment;

import comflower.sagongsa.common.exception.UnknownCommentException;
import comflower.sagongsa.common.exception.UnknownPostException;
import comflower.sagongsa.comment.projection.UserCommentProjection;
import comflower.sagongsa.post.PostRepository;
import comflower.sagongsa.comment.request.CreateCommentRequest;
import comflower.sagongsa.comment.request.EditCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<UserCommentProjection> getCommentsByPost(Long postId) {
        if (postRepository.findById(postId).isEmpty()) {
            throw new UnknownPostException();
        }

        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(Long authorId, Long postId, CreateCommentRequest request) {
        if (postRepository.findById(postId).isEmpty()) {
            throw new UnknownPostException();
        }

        Long parentId = request.getParentId();
        if (parentId != null && commentRepository.findById(request.getParentId()).isEmpty()) {
            throw new UnknownCommentException();
        }

        Comment comment = Comment.builder()
                .postId(postId)
                .authorId(authorId)
                .content(request.getContent())
                .createdAt(System.currentTimeMillis())
                .parentId(request.getParentId())
                .build();
        return commentRepository.save(comment);
    }

    public Comment editComment(Long postId, Long commentId, EditCommentRequest request) {
        if (postRepository.findById(postId).isEmpty()) {
            throw new UnknownPostException();
        }

        Comment comment = getComment(commentId);
        comment.setContent(request.getContent());
        comment.setEditedAt(System.currentTimeMillis());

        return commentRepository.save(comment);
    }

    public void deleteComment(Long postId, Long commentId) {
        if (postRepository.findById(postId).isEmpty()) {
            throw new UnknownPostException();
        }

        Comment comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(UnknownCommentException::new);
    }
}
