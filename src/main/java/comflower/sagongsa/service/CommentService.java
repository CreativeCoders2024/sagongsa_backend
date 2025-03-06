package comflower.sagongsa.service;

import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.exception.UnknownCommentException;
import comflower.sagongsa.exception.UnknownPostException;
import comflower.sagongsa.repository.CommentRepository;
import comflower.sagongsa.repository.PostRepository;
import comflower.sagongsa.request.CreateCommentRequest;
import comflower.sagongsa.request.EditCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<Comment> getCommentsByPost(Long postId) {
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
                .createdAt(LocalDateTime.now())
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
        comment.setEditedAt(LocalDateTime.now());

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
