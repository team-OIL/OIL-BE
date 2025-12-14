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
    public TokenResponse execute(String refreshToken) {

        // 1. Refresh Token 추출
        //String refreshToken = jwtTokenProvider.resolveToken(request);
        if (refreshToken == null || !jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new OILException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 2. JWT에서 userId 추출
        //Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        // 3. Redis에서 userId 기준 RefreshToken 조회
        RefreshToken storedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new OILException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

        // 4. 토큰 값 비교
        if (!storedToken.getRefreshToken().equals(refreshToken)) {
            throw new OILException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        // 5. 기존 RefreshToken 삭제 (rotation)
        //refreshTokenRepository.deleteById(userId);

        // 새 토큰 생성
        TokenResponse tokenResponse = jwtTokenProvider.createToken(storedToken.getUserId());

        // 🔥 여기서 update
        storedToken.update(
                tokenResponse.refreshToken(),
                jwtProperties.refreshTokenExpiration()
        );

        // Redis는 dirty checking 안 되므로 save 필요
        //refreshTokenRepository.save(storedToken);

        // 6. 새 토큰 발급 (내부에서 RefreshToken 저장됨)
        return tokenResponse;
    }
}
