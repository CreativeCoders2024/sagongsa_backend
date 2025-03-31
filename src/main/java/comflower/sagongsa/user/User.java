package comflower.sagongsa.user;

import comflower.sagongsa.comment.Comment;
import comflower.sagongsa.contest.Contest;
import comflower.sagongsa.post.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    private String introduction;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isManager;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isWithdrawn;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int field;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Avatar avatar;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Contest> contests;
}
