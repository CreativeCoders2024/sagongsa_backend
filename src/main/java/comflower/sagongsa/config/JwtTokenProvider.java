package comflower.sagongsa.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys; // 추가된 import
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey; // 추가된 import
import java.util.Date;

@Component
public class JwtTokenProvider {

    private SecretKey secretKey; // 기존 String에서 SecretKey로 변경

    @Value("${jwt.token-validity-in-seconds}")
    private long validityInMilliseconds;

    // secretKey 초기화
    @PostConstruct
    protected void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 256비트 이상 안전한 키 생성
    }

    // JWT 토큰 생성
    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 새로운 키와 알고리즘 설정
                .compact();
    }

    // 토큰에서 사용자 이름 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 요청 헤더에서 JWT 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
