package comflower.sagongsa.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "avatar")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_avatar_user"))
    private User user;
}
