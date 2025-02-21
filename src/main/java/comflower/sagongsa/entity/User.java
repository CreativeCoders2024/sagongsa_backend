package comflower.sagongsa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자를 생성해주는 친구
@AllArgsConstructor  //모든 필드를 매개변수로 가지는 생성자를 생성해주는 친구
@Builder  // Builder Pattern
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

    private String profileImg;  //blob 형식을 spring에서 어떻게 쓰는지.. -> 나중에!
}
