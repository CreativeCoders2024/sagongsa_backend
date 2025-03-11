package comflower.sagongsa.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자를 생성해주는 친구
@AllArgsConstructor  // 모든 필드를 매개변수로 받는 생성자를 생성해주는 친구
@Builder  // 빌더 패턴을 적용
@Getter
@Setter
@Entity  // JPA 엔티티
@Table(name = "contest")  // 데이터베이스의 contest 테이블과 매핑됨을 나타내는 어노테이션
public class Contest {
    @Id //기본키로 설정 자동으로 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String title;

    private String thumbnail;

    @Column(nullable = false)
    private String prize;

    @Column(nullable = false)
    private long startedAt;

    @Column(nullable = false)
    private long endedAt;

    private String link;

    @Column(nullable = false)
    private int topic;
}
