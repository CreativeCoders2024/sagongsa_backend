package comflower.sagongsa.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@Entity
@Table(name = "post")
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
