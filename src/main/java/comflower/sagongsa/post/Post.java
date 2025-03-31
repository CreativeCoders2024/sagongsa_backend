package comflower.sagongsa.post;

import comflower.sagongsa.comment.Comment;
import comflower.sagongsa.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false, nullable = false)
    private User author;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
