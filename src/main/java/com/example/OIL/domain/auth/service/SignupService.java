package com.example.OIL.domain.auth.service;

import com.example.OIL.domain.auth.exception.AuthErrorCode;
import com.example.OIL.domain.auth.presentation.dto.request.SignUpRequest;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.global.error.exception.OILException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(SignUpRequest request) {

        // 1) 이메일 중복 체크
        if (userRepository.existsByEmail(request.email())) {
            throw new OILException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // 2) User 생성
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .userName(request.userName())            // ← 네 엔티티 필드에 맞춤
                .missionTime(request.missionTime())
                .isAlarmEnabled(request.isAlarmEnabled())
                .build();

        // 3) 저장
        userRepository.save(user);
    }

}
