package com.example.OIL.domain.auth.service;

import com.example.OIL.domain.auth.domain.repository.RefreshTokenRepository;
import com.example.OIL.domain.user.service.UserFacade;
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

        // 1. 현재 로그인한 유저의 userId 가져오기
        Long userId = userFacade.getCurrentUser().getId();

        // 2. Redis에서 RefreshToken 삭제
        refreshTokenRepository.deleteById(userId);
    }
}
