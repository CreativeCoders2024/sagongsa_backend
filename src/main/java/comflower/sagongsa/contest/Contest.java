package comflower.sagongsa.contest;

import comflower.sagongsa.user.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@Entity
@Table(name = "contest")
public class Contest {
    @Id //기본키로 설정 자동으로 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false, nullable = false)
    private User author;

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
