package comflower.sagongsa.repository;

import comflower.sagongsa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MypageRepository extends JpaRepository<User, Long> {

}
