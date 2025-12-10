package com.example.OIL.domain.auth.service;

import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePushTokenService {
    private final UserFacade userFacade;

    @Transactional
    public void execute() {
        User user = userFacade.getCurrentUser();

        user.deletePushToken();
    }
}
