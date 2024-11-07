package comflower.sagongsa.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface UserDetails extends Serializable {  //쓸때…! build.gradle에서 의존성 주입하는거 잊지 말자!
    // 계정이 가지고 있는 권한 목록 리턴
    Collection<? extends GrantedAuthority> getAuthorities();

    // 차례대로, 계정 이름, 비밀번호 리턴
    String getUsername();
    String getPassword();

    // 계정이 만료됐는지 리턴 -> ture : 만료되지 않음을 의미
    boolean isAccountNonExpired();
    // 계정이 잠겨있는지 리턴 -> ture : 잠기지 않음을 의미
    boolean isAccountNonLocked();
    // 비밀번호가 만료됐는지 리턴 -> true : 만료되지 않음을 의미
    boolean isCredentialsNonExpired();
    // 계정이 활성화 되어있는지 리턴 -> true : 활성화 상태를 의미
    boolean isEnabled();


}