package comflower.sagongsa.comment;

import comflower.sagongsa.comment.projection.UserCommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c as comment, c.author as author FROM Comment c WHERE c.post.id = :postId")
    List<UserCommentProjection> findByPostId(@Param("postId") Long postId);
}
