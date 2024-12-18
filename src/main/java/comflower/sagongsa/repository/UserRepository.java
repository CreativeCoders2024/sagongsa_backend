package comflower.sagongsa.repository;

import comflower.sagongsa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getById(String id);
    Optional<User> getByUserId(Long userId);  // 얘도 지우고 싹 다 getById로 통일
}
