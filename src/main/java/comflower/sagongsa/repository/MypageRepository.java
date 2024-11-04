package comflower.sagongsa.repository;

import comflower.sagongsa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MypageRepository extends JpaRepository<User, Long> {

    // 사용자 ID로 사용자 정보를 찾는 메서드
    Optional<User> findByUserId(Long userId);

    // 사용자의 nickname으로 사용자 정보를 찾는 메서드
    Optional<User> findByNickname(String nickname);

    // 사용자의 이메일로 사용자 정보를 찾는 메서드
    Optional<User> findByEmail(String email);
}
