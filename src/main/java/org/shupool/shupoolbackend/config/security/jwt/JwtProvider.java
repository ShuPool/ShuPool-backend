package org.shupool.shupoolbackend.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shupool.shupoolbackend.config.security.auth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String salt;

    private Key secretKey;

    private final long exp = 1000L * 60 * 60 * 24;

    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public TokenInfo createToken(String socialId) {
        Claims claims = Jwts.claims().setSubject(socialId);
        Date now = new Date();

        // Access Token 생성
        String accessToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + exp))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
            .setExpiration(new Date(now.getTime() + exp))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();

        return TokenInfo.builder()
            .grantType("Bearer")
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getSocialId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저 socialId 획득
    public String getSocialId(String token) {
        String subject = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody()
            .getSubject();
        return subject;
    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String socialIdFromRequest(HttpServletRequest request) {
        String token = resolveToken(request).substring(7);
        String subject = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody()
            .getSubject();
        return subject;
    }
}
