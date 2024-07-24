package comflower.sagongsa.service;

import comflower.sagongsa.dto.CreateCommentDTO;
import comflower.sagongsa.dto.EditCommentDTO;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

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
                .parent(createCommentDTO.getParent())
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
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id : " + commentId + " not found"));

        comment.setContent(editCommentDTO.getContent());
        comment.setParent(editCommentDTO.getParent());
        comment.setEditedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }
}
