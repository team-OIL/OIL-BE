package com.example.OIL.domain.auth.service;

import com.example.OIL.domain.auth.domain.entity.RefreshToken;
import com.example.OIL.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.OIL.domain.auth.exception.AuthErrorCode;
import com.example.OIL.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OIL.global.error.exception.OILException;
import com.example.OIL.global.security.jwt.JwtProperties;
import com.example.OIL.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Transactional
    public TokenResponse execute(HttpServletRequest request) {
        // 1. Refresh Token 파싱 및 검증
        String refreshToken = parseAndValidate(request);

        // 2. 이메일 추출 및 Refresh Token 삭제
        String email = getEmailAndDeleteRefreshToken(refreshToken);

        // 3. 새로운 AccessToken 및 RefreshToken 발급
        TokenResponse tokenResponse = jwtTokenProvider.createToken(email);

        // 4. 새로 발급된 Refresh Token Redis에 저장
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .email(email)
                        .refreshToken(tokenResponse.refreshToken())
                        .ttl(jwtProperties.refreshTokenExpiration()) // 새로 발급한 refresh token의 ttl 설정
                        .build()
        );

        return tokenResponse;
    }

    // 1. Refresh Token 파싱 및 검증
    private String parseAndValidate(HttpServletRequest request) {

        String rfToken = jwtTokenProvider.resolveToken(request);
        /* String parseToken = jwtTokenProvider.parseToken(rfToken);
        System.out.println(parseToken);
        System.out.println(parseToken);
        System.out.println(parseToken);
        System.out.println(parseToken);*/
        if (rfToken == null || !jwtTokenProvider.validateRefreshToken(rfToken)) {
            throw new OILException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        return rfToken;
    }

    // 2. Redis에서 Refresh Token 찾고 삭제 후 이메일 반환
    private String getEmailAndDeleteRefreshToken(String refreshToken) {
        RefreshToken redisRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new OILException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

        String email = redisRefreshToken.getEmail();
        refreshTokenRepository.delete(redisRefreshToken); // Redis에서 기존 Refresh Token 삭제

        return email;
    }
}
