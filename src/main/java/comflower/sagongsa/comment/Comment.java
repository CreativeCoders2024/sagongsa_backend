package comflower.sagongsa.comment;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id //기본키로 설정 자동으로 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private long createdAt;

    private Long editedAt;

    private Long parentId;
}
