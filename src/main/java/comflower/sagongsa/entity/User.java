package comflower.sagongsa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자를 생성해주는 친구
@AllArgsConstructor  //
@Builder  // Builder Pattern
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String introduction;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean is_manager;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean is_withdrawn;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int field;

    @Column(nullable = true)
    private String profile_img;  //blob 형식을 spring에서 어떻게 쓰는지..

    public void update(String pw, String nickname) {
        this.pw = pw;
        this.nickname = nickname;
    }
}