package comflower.sagongsa.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String pw;  // 비밀번호 필드

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String introduction;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isWithdrawn;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int field;

    @Column(nullable = true)
    private String profileImg;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    // 권한을 반환하는 메서드 추가
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getPassword() {
        return this.pw;  // 비밀번호 반환
    }

    public String getUsername() {
        return this.id;  // 사용자 ID 반환
    }

    public boolean isWithdrawn() {
        return this.isWithdrawn;  // 탈퇴 여부 반환
    }
}
