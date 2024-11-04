package comflower.sagongsa.entity;

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
    private String id; // 로그인 ID

    @Column(nullable = false)
    private String pw; // 비밀번호

    @Column(nullable = false)
    private String nickname; // 별명

    @Column(nullable = false)
    private String email; // 이메일

    @Column(nullable = false, unique = true)
    private String username; // 사용자 이름 추가

    @Column(nullable = true)
    private String introduction; // 자기소개

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isWithdrawn; // 탈퇴 여부

    @Column(nullable = false)
    @ColumnDefault("0")
    private int field; // 분야

    @Column(nullable = true)
    private String profileImg; // 프로필 이미지

    // 추가된 메서드
    public String getPassword() {
        return this.pw;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>(); // roles 필드를 정의해야 함
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
