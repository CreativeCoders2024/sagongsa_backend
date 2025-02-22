package comflower.sagongsa.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHelper {
    private final Logger logger = LoggerFactory.getLogger(JwtHelper.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-in-ms}")
    private long jwtExpirationInMs;

    public String generateToken(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        var key = Keys.hmacShaKeyFor(Encoders.BASE64.encode(jwtSecret.getBytes()).getBytes());

        return Jwts.builder()
                .subject(Long.toString(userId))
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        var key = Keys.hmacShaKeyFor(Encoders.BASE64.encode(jwtSecret.getBytes()).getBytes());
        var parser = Jwts.parser().verifyWith(key).build();

        try {
            return parser.parseSignedClaims(token);
        } catch (SignatureException e) {
            logger.debug("Invalid JWT submitted", e);
        } catch (ExpiredJwtException e) {
            logger.debug("Expired JWT submitted (Expired at {})", e.getClaims().getExpiration());
        } catch (NumberFormatException e) {
            logger.debug("JWT subject is not a number", e);
        } catch (Exception e) {
            logger.error("Failed to parse JWT token", e);
        }

        return null;
    }
}
