package com.example.OIL.global.security.jwt;

import com.example.OIL.domain.auth.domain.entity.RefreshToken;
import com.example.OIL.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.OIL.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OIL.global.exeption.ExpiredJwt;
import com.example.OIL.global.exeption.InvalidJwt;
import com.example.OIL.global.security.OILUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    private SecretKey secretKey;

    private static final String TYPE = "type";
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Access / RefreshToken 공통 생성 메서드
     */
    private String generateToken(Long userId, String type, Long expirationSeconds) {
        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expirationSeconds));

        return Jwts.builder()
                .subject(String.valueOf(userId)) // ⭐ userId
                .issuedAt(now)
                .expiration(expiry)
                .claim(TYPE, type)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }


    /**
     * Access token 생성
     */
    private String generateAccessToken(Long userId) {
        return generateToken(userId, ACCESS, jwtProperties.accessTokenExpiration());
    }

    /**
     * Refresh token 생성 + DB 저장
     */
    private String generateRefreshToken(Long userId) {
        String token = generateToken(userId, REFRESH, jwtProperties.refreshTokenExpiration());
        
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .userId(userId)
                        .refreshToken(token)
                        .ttl(jwtProperties.refreshTokenExpiration())
                        .build()
        );
        return token;
    }

    public TokenResponse createToken(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        log.info("CREATE TOKEN userId={}", userId);
        System.out.println("1234123412341234");
        System.out.println(userId);
        return TokenResponse.builder()
                .accessToken(generateAccessToken(userId))
                .refreshToken(generateRefreshToken(userId))
                .accessTokenExpiresAt(now.plusSeconds(jwtProperties.accessTokenExpiration()))
                .refreshTokenExpiresAt(now.plusSeconds(jwtProperties.refreshTokenExpiration()))
                .build();
    }


    /**
     * Authorization 헤더에서 Bearer 토큰 꺼내기
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.header());
        return parseBearerToken(bearerToken);
    }

    /**
     * Bearer 제거 (substring 방식 → 안전)
     */
    private String parseBearerToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.prefix())) {
            return bearerToken.substring(jwtProperties.prefix().length()).trim();
        }
        return null;
    }


    /**
     * Claims 파싱
     */
    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            // 로그 추가하여 예외의 원인 확인
            //System.out.println("Exception occurred while parsing token: " + e.getMessage());

            if (e instanceof io.jsonwebtoken.ExpiredJwtException) {
                throw ExpiredJwt.EXCEPTION;
            } else {
                throw InvalidJwt.EXCEPTION;
            }
        }
    }



    /**
     * Refresh Token인지 확인
     */
    public boolean isRefreshToken(String token) {
        return REFRESH.equals(getClaims(token).get(TYPE));
    }



    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    /**
     * AccessToken 유효성 검사
     */
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = getClaims(token);

            // refresh 타입인지 + 서명/만료 검증만
            return REFRESH.equals(claims.get(TYPE));
        } catch (Exception e) {
            return false;
        }
    }

}
