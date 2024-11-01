
package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities(); // User 엔티티에서 권한 가져오기
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