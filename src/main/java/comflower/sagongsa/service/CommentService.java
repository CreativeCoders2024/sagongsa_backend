package comflower.sagongsa.service;

import comflower.sagongsa.entity.Comment;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.exception.UnknownCommentException;
import comflower.sagongsa.exception.UnknownPostException;
import comflower.sagongsa.repository.CommentRepository;
import comflower.sagongsa.repository.PostRepository;
import comflower.sagongsa.request.CreateCommentRequest;
import comflower.sagongsa.request.EditCommentRequest;
import comflower.sagongsa.response.CommentWithUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

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

    public List<CommentWithUser> getCommentsWithUserByPost(Long postId) {
        List<Object[]> results = commentRepository.findWithUserByPostId(postId);

        return results
                .stream()
                .map(row -> {
                    Comment comment = (Comment) row[0];
                    User user = (User) row[1];
                    return new CommentWithUser(
                            comment.getId(),
                            comment.getPostId(),
                            comment.getAuthorId(),
                            comment.getContent(),
                            comment.getCreatedAt(),
                            comment.getEditedAt(),
                            comment.getParentId(),
                            user
                    );
                })
                .collect(Collectors.toList());
    }
}
