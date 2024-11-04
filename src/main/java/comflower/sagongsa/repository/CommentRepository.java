package comflower.sagongsa.repository;

import comflower.sagongsa.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
    public interface CommentRepository extends JpaRepository<Comment, Long> {
        Optional<Comment> findByCommentId(Long commentId);
        List<Comment> findByPostId(Long postId);

    }

