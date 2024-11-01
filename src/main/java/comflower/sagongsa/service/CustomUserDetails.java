package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한 반환 로직, 현재는 빈 리스트로 설정. 필요에 따라 권한 추가 가능
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPw();
    }

    @Override
    public String getUsername() {
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 계정 만료 상태가 아닙니다.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 계정 잠금 상태가 아닙니다.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 비밀번호 만료 상태가 아닙니다.
    }

    @Override
    public boolean isEnabled() {
        return !user.isWithdrawn();  // `isWithdrawn` 필드가 true인 경우 계정이 비활성화됨
    }
}
