package comflower.sagongsa.config;


import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // 빈 사용
@EnableWebSecurity  // 시큐리티 활성화
@RequiredArgsConstructor  // Lombok 어노테이션 final 필드나 NonNull로 선언된 필드를 포함하는 생성자를 자동으로 생성합니다.
public class SecurityConfig {

    // CORS(Cross-Origin Resource Sharing) 필터를 주입받는 필드입니다.
    //CORS 허용으로 바꾸는 이유 = 브라우저는 동일 출처 정책 즉 예를 들어 www. sagongsa에서
    // api.sagongsa로 가는게 기본적으로 막아둠(보안상의 이유) 그래서 이걸 허용으로 바꿔야함
    // 동일 출처면 필요 없음 동일 출처란 Protocol : http,s 과 Host : 사이트 도메인 Port : 포트번호 까지만 같아도된다.
    // 이는 다른 도메인에서 들어오는 요청을 허용할지 여부를 결정하는 데 사용됩니다.
    private final CorsFilter corsFilter;

    // Spring Security에서 필터 체인(SecurityFilterChain)을 정의하는 메서드입니다.
    // HTTP 요청이 들어왔을 때 이 필터 체인이 적용되어 보안 관련 로직을 처리하게 됩니다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 공격 방지를 비활성화
                // JWT(JSON Web Token)를 사용하는 경우, CSRF 보호가 필요 없으므로 disable() 메서드로 비활성화.
                .csrf(csrf -> csrf.disable())

                // 세션을 사용하지 않는 무상태(stateless) 방식을 설정. JWT를 사용하기 때문
                // 서버가 세션을 관리하지 않고, 요청마다 클라이언트가 JWT 토큰을 통해 인증을 받는 방식
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // CORS 필터를 추가. 이 필터는 다른 도메인에서 오는 요청을 허용할지 여부를 결정하는 데 사용
                .addFilter(corsFilter)

                // Spring Security에서 기본적으로 제공하는 폼 기반 로그인 기능을 비활성화
                // JWT 인증을 사용하는 경우 사용자가 별도의 로그인 페이지를 통해 인증하는 방식이 필요하지 않으므로 비활성화
                .formLogin(form -> form.disable())

                // HTTP 기본 인증(HTTP Basic Authentication)을 비활성화
                // JWT와 같은 방식의 토큰 인증을 사용할 것이므로, HTTP 기본 인증은 필요 X
                .httpBasic(basic -> basic.disable())

                // 요청에 대한 접근 권한을 설정 부분
                // 여기서는 특정 경로에 대해 어떤 사용자만 접근할 수 있는지 설정.
                .authorizeHttpRequests(auth -> auth // --> 우리끼리 상의
                        // 'v1/api/member' 경로에 대한 요청은 'USER', 'MANAGER', 'ADMIN' 역할을 가진 사용자만 접근.
                        // 'requestMatchers'를 사용해 경로를 지정하고, 'hasAnyRole'을 사용해 여러 역할을 설정.
                        .requestMatchers("v1/api/member", "v1/api/member", "v1/api/member")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")

                        // 'v1/api/admin/**' 경로에 대한 요청은 'ADMIN' 역할을 가진 사용자만 접근할 수 있도록 설정.
                        .requestMatchers("v1/api/admin/**")
                        .hasRole("ADMIN")  // 우리끼리 상의 <--

                        // 그 외 모든 요청은 인증 없이 접근을 허용합니다.
                        // 이는 API에서 인증이 필요 없는 경로나 리소스에 대해 접근을 허용하는 설정.
                        .anyRequest().permitAll()
                );

        // 최종적으로 구성된 필터 체인을 반환합니다.
        return http.build();
    }
}
