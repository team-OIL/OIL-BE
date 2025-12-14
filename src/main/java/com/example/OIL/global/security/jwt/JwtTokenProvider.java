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
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final OILUserDetailsService oilUserDetailsService;
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
    private String generateToken(String email, String type, Long expirationMillis) {
        Date now = new Date();
        Date expiry = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expirationMillis));

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiry)
                .claim(TYPE, type)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Access token 생성
     */
    private String generateAccessToken(String email) {
        return generateToken(
                email,
                ACCESS,
                jwtProperties.accessTokenExpiration()
        );
    }

    /**
     * Refresh token 생성 + DB 저장
     */
    private String generateRefreshToken(String email) {
        String refreshToken = generateToken(
                email,
                REFRESH,
                jwtProperties.refreshTokenExpiration()
        );

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .email(email)
                        .refreshToken(refreshToken)
                        .ttl(jwtProperties.refreshTokenExpiration())
                        .build()
        );

        return refreshToken;
    }

    public TokenResponse createToken(String email) {
        LocalDateTime now = LocalDateTime.now();

        return TokenResponse.builder()
                .accessToken(generateAccessToken(email))
                .refreshToken(generateRefreshToken(email))
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

    /**
     * DB에도 Refresh Token이 존재하는지 확인
     */
    public boolean validateRefreshTokenFromDB(String token) {
        return refreshTokenRepository.findByRefreshToken(token).isPresent();
    }

    /**
     * 이메일(subject) 꺼내기
     */
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
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

    /**
     * JWT → Authentication 변환 (SecurityContext에 넣기)
     */
    public Authentication authentication(String token) {
        String email = getEmailFromToken(token);
        UserDetails userDetails = oilUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Refresh Token 검증 메서드
    public boolean validateRefreshToken(String token) {
        try {
            // Claims 파싱
            Claims claims = getClaims(token);

            // refresh token인지 확인
            if (!REFRESH.equals(claims.get("type"))) {
                return false; // type이 refresh가 아니라면 false 반환
            }

            // DB에서도 해당 refresh token이 존재하는지 확인
            return validateRefreshTokenFromDB(token);
        } catch (Exception e) {
            return false; // 예외 발생 시 false 반환
        }
    }
}
