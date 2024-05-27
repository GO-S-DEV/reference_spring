package com.example.reference.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {


    private final Logger logger = Logger.getLogger(String.valueOf(JwtTokenProvider.class));
    private final String secretKey;
    private final String AUTHORITIES_KEY;
    private final Long accessTokenValidityInMilliseconds; // 액세스 토큰 유효시간
    private final Long refreshTokenValidityInMilliseconds; // 리프레시 토큰 유효시간
    private Key key; // 비밀키를 이용하여 토큰을 만들 때 사용

    public JwtTokenProvider(
        @Value("${jwt.secret}") String secretKey,
        @Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInMilliseconds,
        @Value("${jwt.roles}") String authoritiesKey,
        @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidityInMilliseconds
    ) {
        this.secretKey = secretKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.AUTHORITIES_KEY = authoritiesKey;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = secretKey.getBytes(); // 키를 바이트로 바꿈
        this.key = Keys.hmacShaKeyFor(keyBytes); // 키 생성
    }

    private HttpServletRequest getCurrentHttpRequest() {
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            logger.info("getCurrentHttpRequest() error : " + e.getMessage());
            return null;
        }
    }

    public String createAccessToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date expirationTime = new Date(now + accessTokenValidityInMilliseconds); // 토큰 만료 시간
        // 권한 정보를 문자열로 가져옴
        Claims claims = generateClaims(authentication, expirationTime);
        return generateToken(claims);
    }

    private Claims generateClaims(Authentication authentication, Date expirationTime) {
        Claims claims = Jwts.claims();
        claims.put(AUTHORITIES_KEY, authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        claims.setExpiration(expirationTime);
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setSubject(authentication.getName());
        return claims;
    }

    private String generateToken(Claims claims) {
        return Jwts.builder()
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date expirationTime = new Date(now + refreshTokenValidityInMilliseconds); // 토큰 만료 시간

        // 권한 정보를 문자열로 가져옴
        Claims claims = generateClaims(authentication, expirationTime);
        String compact = generateToken(claims);

        //:TODO Redis 에 저장 추후 추가

        return compact;
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public void validateSocketToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
    }

    public Authentication getAuthentication(String token) {
        HttpServletRequest request = getCurrentHttpRequest();

        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            User principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        }catch (ExpiredJwtException e) {
            logger.info("토큰이 만료되었습니다." + e.getMessage());
            Objects.requireNonNull(request).setAttribute("expired", e.getMessage());
            return null;
        }
        catch (Exception e) {
            logger.info("getAuthentication() error : " + e.getMessage());
            return null;
        }
    }

    public String getAuthenticationName(String token) {

        HttpServletRequest request = getCurrentHttpRequest();

        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        }catch (ExpiredJwtException e) {
            logger.info("getAuthenticationName() error : " + e.getMessage());
            Objects.requireNonNull(request).setAttribute("expired", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.info("getAuthenticationName() error : " + e.getMessage());
            return null;
        }
    }

    public String getAuthenticationNameToSocket(String token) {

        HttpServletRequest request = getCurrentHttpRequest();

        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        }catch (ExpiredJwtException e) {
            logger.info("getAuthenticationNameToSocket() error : " + e.getMessage());
            Objects.requireNonNull(request).setAttribute("expired", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.info("getAuthenticationNameToSocket() error : " + e.getMessage());
            return null;
        }
    }






}
