package comflower.sagongsa.repository;

import comflower.sagongsa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);  // ID로 사용자 찾기
    Optional<User> findByUserId(Long userId);  // UserId로 사용자 찾기 추가
    Optional<User> findByUsername(String username);  // Username으로 사용자 찾기 추가
}
