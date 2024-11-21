package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
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

    public Comment createComment(Long authorId, Long postId, CreateCommentDTO createCommentDTO) {
        Comment comment = Comment.builder()
                .postId(postId)
                .authorId(authorId)
                .content(createCommentDTO.getContent())
                .createdAt(LocalDateTime.now())
                .parentId(createCommentDTO.getParentId())
                .build();
        return commentRepository.save(comment);
    }

    public Comment editComment(Comment comment, EditCommentDTO editCommentDTO) {
        comment.setContent(editCommentDTO.getContent());
        comment.setEditedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
