package comflower.sagongsa.repository;


import comflower.sagongsa.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    // contestId로 Contest 엔티티를 검색
    Optional<Contest> findByContestId(Long contestId);
    // userId로 Contest 엔티티들을 검색
    List<Contest> findByUserId(Long userId);
    // title로 Contest 엔티티를 검색
    Optional<Contest> findByTitle(String title);
    // prize로 Contest 엔티티들을 검색
    List<Contest> findByPrize(String prize);
}
