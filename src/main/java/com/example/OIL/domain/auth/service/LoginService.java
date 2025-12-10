package com.example.OIL.domain.auth.service;

import com.example.OIL.domain.auth.exception.AuthErrorCode;
import com.example.OIL.domain.auth.presentation.dto.request.LoginRequest;
import com.example.OIL.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.domain.user.exception.UserErrorCode;
import com.example.OIL.global.error.exception.OILException;
import com.example.OIL.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public TokenResponse execute (LoginRequest request) {
        // 1) 이메일로 유저 조회
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new OILException(UserErrorCode.USER_NOT_FOUND));

        // 2) 비밀번호 비교
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new OILException(AuthErrorCode.INVALID_CREDENTIALS);
        }


        TokenResponse tokenResponse = jwtTokenProvider.createToken(request.email());

        // 7) 응답 반환
        return new TokenResponse(
                tokenResponse.accessToken(),
                tokenResponse.refreshToken(),
                tokenResponse.accessTokenExpiresAt(),
                tokenResponse.refreshTokenExpiresAt()
        );
    }
}
