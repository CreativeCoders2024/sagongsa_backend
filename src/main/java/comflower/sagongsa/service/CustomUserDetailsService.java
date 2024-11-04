package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // id를 사용하여 사용자 조회
        User user = userRepository.findById(id)  // username 대신 id로 사용
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + id));

        return new CustomUserDetails(user);  // CustomUserDetails는 UserDetails 구현 클래스
    }
}
