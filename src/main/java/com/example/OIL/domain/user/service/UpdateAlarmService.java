package com.example.OIL.domain.user.service;

import com.example.OIL.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAlarmService {

    private final UserFacade userFacade;

    @Transactional
    public void execute(boolean alarmEnabled) {

        User currentUser = userFacade.getCurrentUser();
        currentUser.updateAlarm(alarmEnabled);
    }
}
