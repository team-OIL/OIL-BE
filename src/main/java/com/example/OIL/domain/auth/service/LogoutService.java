package com.example.OIL.domain.auth.service;

import com.example.OIL.domain.auth.domain.entity.RefreshToken;
import com.example.OIL.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.OIL.domain.auth.exception.AuthErrorCode;
import com.example.OIL.domain.user.service.UserFacade;
import com.example.OIL.global.error.exception.OILException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final UserFacade userFacade;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void execute() {
        String email = userFacade.getCurrentUser().getEmail();

        // 리프레시 토큰을 찾아 삭제
        RefreshToken refreshToken = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new OILException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

        refreshTokenRepository.delete(refreshToken);

        // 추가적으로 리프레시 토큰 만료 시간을 설정하거나, 다른 처리가 필요할 수 있습니다.
        // 예를 들어, 사용자가 JWT를 재발급 받지 못하도록 블랙리스트에 추가하는 등의 작업을 고려할 수 있습니다.
    }
}
