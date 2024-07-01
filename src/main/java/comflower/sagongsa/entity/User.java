package comflower.sagongsa.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

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
}