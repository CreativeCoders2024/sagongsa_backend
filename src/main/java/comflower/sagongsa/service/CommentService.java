package comflower.sagongsa.service;

import comflower.sagongsa.request.CreateCommentRequest;
import comflower.sagongsa.request.EditCommentRequest;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.CommentNotFoundException;
import comflower.sagongsa.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public boolean isCommentPresentById(Long commentId) {
        return commentRepository.findById(commentId).isPresent();
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPostId(post.getId());
    }

    public Comment createComment(Long authorId, Long postId, CreateCommentRequest body) {
        Comment comment = Comment.builder()
                .postId(postId)
                .authorId(authorId)
                .content(body.getContent())
                .createdAt(LocalDateTime.now())
                .parentId(body.getParentId())
                .build();
        return commentRepository.save(comment);
    }

    public Comment editComment(Comment comment, EditCommentRequest body) {
        comment.setContent(body.getContent());
        comment.setEditedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
