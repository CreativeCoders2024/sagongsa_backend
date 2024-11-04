package comflower.sagongsa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "contest")
public class Contest {
    @Id // 기본키로 설정, 자동으로 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contestId;

    @Column(nullable = false)
    private Long userId; // 공모전을 생성한 사용자 ID

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String img;

    @Column(nullable = false)
    private String prize;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime endedAt;

    @Column(nullable = true)
    private String link;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long field; // 분야
}
