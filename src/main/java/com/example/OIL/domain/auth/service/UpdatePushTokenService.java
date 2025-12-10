package com.example.OIL.domain.auth.service;

import com.example.OIL.domain.auth.presentation.dto.request.TokenRequest;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePushTokenService {

    private final UserFacade userFacade;

    @Transactional
    public void execute(TokenRequest request) {
        User user = userFacade.getCurrentUser();

        user.updatePushToken(request.pushToken());
    }
}
