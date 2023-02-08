package pl.thesis.security.config;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import pl.thesis.security.services.model.UserDetailsDefault;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${thesis.app.jwtSecret}")
    private String jwtSecret;

    @Value("${thesis.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${thesis.app.jwtCookieName}")
    private String jwtCookie;


    public String getJwtFromCookies(HttpServletRequest request){
            var cookie = WebUtils.getCookie(request, jwtCookie);

        if (cookie == null){
            return null;
        }
        return cookie.getValue();
    }

    public ResponseCookie generateJwtCookie(UserDetailsDefault userDetails) {
        String jwt = generateTokenFromUserLogin(userDetails.getUsername());

        return ResponseCookie
                .from(jwtCookie, jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie
                .from(jwtCookie, "")
                .path("/api")
                .build();
    }

    public String getLoginFromJwtToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private String generateTokenFromUserLogin (String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
