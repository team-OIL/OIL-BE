package com.example.OIL.domain.user.facade;

import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.domain.user.exception.UserErrorCode;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new OILException(UserErrorCode.USER_NOT_FOUND));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new OILException(UserErrorCode.USER_NOT_FOUND));
    }
}
