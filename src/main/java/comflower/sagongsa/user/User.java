package comflower.sagongsa.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"avatar"})
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
}
