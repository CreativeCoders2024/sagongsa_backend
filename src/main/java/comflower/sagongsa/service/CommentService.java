package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(Long postId, CreateCommentDTO createCommentDTO) {
        throw new UnsupportedOperationException("로그인 화이팅");
//        Comment comment = Comment.builder()
//                .postId(postId)
//                .userId(1L) // 임의로 설성 userId
//                .content(createCommentDTO.getContent())
//                .createdAt(LocalDateTime.now())
//                .parentId(createCommentDTO.getParent())
//                .build();
//        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Contest with id : " + commentId + " not found"));
        commentRepository.delete(comment);
    }

    @Transactional
    public Comment editComment(Long commentId, EditCommentDTO editCommentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id : " + commentId + " not found"));

        comment.setContent(editCommentDTO.getContent());
        comment.setEditedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    @Transactional
    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
