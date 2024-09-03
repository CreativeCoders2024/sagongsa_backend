package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.CreateCommentDTO;
import comflower.sagongsa.dto.request.EditCommentDTO;
import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.entity.Post;
import comflower.sagongsa.error.CommentNotFoundException;
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

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    public Comment editComment(Comment comment, EditCommentDTO editCommentDTO) {
        comment.setContent(editCommentDTO.getContent());
        comment.setEditedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    // 댓글 생성
    @Transactional
    public Comment createComment(Long postId, CreateCommentDTO createCommentDTO) {
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(1L) // 임의로 설정한 userId
                .content(createCommentDTO.getContent())
                .createdAt(LocalDateTime.now())
                .parentId(createCommentDTO.getParentId())
                .build();

        return commentRepository.save(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    // 특정 게시글의 모든 댓글 가져오기
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Post post) {
        return commentRepository.findByPostId(post.getPostId());
    }
}
