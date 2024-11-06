package comflower.sagongsa.config;

import org.springframework.web.filter.CorsFilter; // 스프링의 CORS 필터를 임포트
import org.springframework.context.annotation.Bean; // 스프링에서 빈을 정의하는 어노테이션
import org.springframework.context.annotation.Configuration; // 설정 파일임을 나타내는 어노테이션
import org.springframework.web.cors.CorsConfiguration; // CORS 설정을 위한 클래스
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // URL 경로 기반으로 CORS 설정을 관리하는 클래스

@Configuration
public class CorsConfig {

    // 이 메서드는 CORS 필터를 빈으로 정의하여 스프링이 관리할 수 있도록 함
    @Bean
    public CorsFilter corsFilter() {
        // URL 기반의 CORS 설정 소스를 생성함
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // CORS 설정을 정의하는 객체를 생성
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 자격 증명(쿠키, 인증 정보)을 허용함
        config.addAllowedOrigin("*"); // 모든 도메인에서 오는 요청을 허용함 (보안상 특정 도메인으로 제한하는 것이 권장됨)
        config.addAllowedHeader("*"); // 모든 헤더를 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드(GET, POST, PUT, DELETE 등)를 허용

        // 특정 URL 경로에 대해 위에서 정의한 CORS 설정을 적용함
        source.registerCorsConfiguration("/v1/api/**", config);  //우리의 경로로

        // 생성된 CORS 설정을 기반으로 필터를 반환함
        return new CorsFilter(source);
    }
}