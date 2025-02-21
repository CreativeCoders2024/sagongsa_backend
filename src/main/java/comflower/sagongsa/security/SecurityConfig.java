package comflower.sagongsa.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/users/@me").authenticated()
                                .requestMatchers(HttpMethod.POST, "/contests").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/contests/{contestId}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/contests/{contestId}").authenticated()
                                .requestMatchers(HttpMethod.POST, "/posts").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/posts/{postId}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/posts/{postId}").authenticated()
                                .requestMatchers(HttpMethod.POST, "/posts/{postId}/comments").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/posts/{postId}/comments/{commentId}").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/posts/{postId}/comments/{commentId}").authenticated()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
