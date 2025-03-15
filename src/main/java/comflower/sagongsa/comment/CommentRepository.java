package comflower.sagongsa.comment;

import comflower.sagongsa.comment.projection.UserCommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c.id as id, " +
            "c.postId as postId, " +
            "c.authorId as authorId, " +
            "c.content as content, " +
            "c.createdAt as createdAt, " +
            "c.editedAt as editedAt, " +
            "c.parentId as parentId, " +
            "u as author " +
            "FROM Comment c LEFT JOIN User u ON c.authorId = u.id WHERE c.postId = :postId")
    List<UserCommentProjection> findByPostId(@Param("postId") Long postId);
}
