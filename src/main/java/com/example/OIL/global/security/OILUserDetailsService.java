package com.example.OIL.global.security;

import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.domain.user.exception.UserErrorCode;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OILUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Security가 인증 처리 시 호출하는 핵심 메서드
     * username = email
     */
    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new OILException(UserErrorCode.USER_NOT_FOUND));

        return new OILUserDetails(user);
    }
}