package com.example.OIL.domain.user.service;

import com.example.OIL.domain.auth.service.UpdatePushTokenService;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.domain.user.exception.UserErrorCode;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UserSettingsService {

    private final UserRepository userRepository;
    private final UserFacade userFacade;

    // ✅ 알림 On/Off 변경
    @Transactional
    public void updateAlarmSetting(boolean alarmEnabled) {

        User currentUser = userFacade.getCurrentUser();
        currentUser.updateAlarm(alarmEnabled);
    }

    // ✅ 미션 받을 시간 변경
    @Transactional
    public void updateMissionReceiveTime(LocalTime time) {
        User currentUser = userFacade.getCurrentUser();

        currentUser.updateMissionTime(time);
    }
}
