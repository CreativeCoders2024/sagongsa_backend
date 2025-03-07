package comflower.sagongsa.repository;

import comflower.sagongsa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c, u FROM Comment c JOIN User u ON c.authorId = u.id WHERE c.postId = :postId")
    List<Object[]> findWithUserByPostId(Long postId);
}
