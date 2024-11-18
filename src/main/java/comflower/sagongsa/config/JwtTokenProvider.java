package comflower.sagongsa.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;

@Slf4j
@Component
public class JwtTokenProvider {
//    private final Key key;
//    밑에서 오류나서 주석처리 해둠
//    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }
}

//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import jakarta.servlet.http.HttpServletRequest;
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//
//    private SecretKey secretKey; // 기존 String에서 SecretKey로 변경
//
//    @Value("${jwt.token-validity-in-seconds}")
//    private long validityInMilliseconds;
//
//    // secretKey 초기화
//    @PostConstruct
//    protected void init() {
//        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 256비트 이상 안전한 키 생성
//    }
//
//    // JWT 토큰 생성
//    public String createToken(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
//        claims.put("roles", userDetails.getAuthorities());
//
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + validityInMilliseconds * 1000);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(secretKey) // SignatureAlgorithm.HS256 제거
//                .compact();
//    }
//
//    // 토큰에서 사용자 이름 추출
//    public String getUsername(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    // JWT 토큰에서 사용자 ID 추출
//    public Long getUserIdFromToken(String token) {
//        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//        return Long.valueOf(claims.getSubject()); // subject에서 사용자 ID를 추출하여 Long으로 변환
//    }
//
//    // 토큰 유효성 검증
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    // 요청 헤더에서 JWT 토큰 추출
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("Authorization");
//    }
//}