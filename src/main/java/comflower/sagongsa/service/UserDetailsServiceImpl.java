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
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  // username = uid = id
        User user = userRepository.getById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId())
                .password(user.getPw())
                .roles(user.getRoles().toString())
                .build();
    }
}