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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(Long postId, CreateCommentDTO createCommentDTO) {
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(1L) // 임의로 설성 userId
                .content(createCommentDTO.getContent())
                .createdAt(LocalDateTime.now())
                .parentId(createCommentDTO.getParentId())
                .build();

        commentRepository.save(comment);

    }
    @Transactional
    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Contest with id : " + commentId + " not found"));
        commentRepository.delete(comment);


    }

    @Transactional
    public void editComment(Long postId, Long commentId, EditCommentDTO editCommentDTO) {
        Comment comment = commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id : " + commentId + " not found"));

        comment.setContent(editCommentDTO.getContent());
        comment.setEditedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    @Transactional
    public List<Comment> getComment(Long postId) {
        return commentRepository.findByPostId(postId);
    }
    // existsById 메서드 추가
    public boolean existsById(Long commentId) {
        return commentRepository.existsById(commentId);
    }
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }
}
