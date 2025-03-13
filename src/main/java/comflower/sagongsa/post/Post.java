package comflower.sagongsa.post;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자를 생성해주는 친구
@AllArgsConstructor  // 모든 필드를 매개변수로 받는 생성자를 생성해주는 친구
@Builder  // 빌더 패턴을 적용
@Getter
@Setter
@Entity  // JPA 엔티티
@Table(name = "post")  // 데이터베이스의 post 테이블과 매핑됨을 나타내는 어노테이션
public class Post {
    @Id  // 기본키로 설정 자동으로 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int memberCount;

    @Column(nullable = false)
    private int maxMemberCount;

    @Column(nullable = false)
    private int topic;

    @Column(nullable = false)
    private long createdAt;

    @Column(nullable = false)
    private long endedAt;
}
