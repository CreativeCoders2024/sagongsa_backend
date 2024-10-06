package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.error.CommentNotFoundException;
import comflower.sagongsa.repository.CommentRepository;
import comflower.sagongsa.repository.PostRepository;
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


    // 댓글 생성
    @Transactional
    public void createComment(Long postId, CreateCommentDTO createCommentDTO) {
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(1L) // 임의로 설정한 userId
                .content(createCommentDTO.getContent())
                .createdAt(LocalDateTime.now())
                .parentId(createCommentDTO.getParentId())
                .build();

        commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        commentRepository.delete(comment);
    }

    // 댓글 수정
    @Transactional
    public void editComment( Long commentId, EditCommentDTO editCommentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        comment.setContent(editCommentDTO.getContent());
        comment.setEditedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    // 특정 게시글의 모든 댓글 가져오기
    @Transactional(readOnly = true)
    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 댓글 ID 존재 여부 확인
    public boolean existsById(Long commentId) {
        return commentRepository.existsById(commentId);
    }

    // 특정 댓글 가져오기
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }
}
