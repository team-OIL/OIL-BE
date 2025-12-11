package com.example.OIL.domain.user.service;

import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.domain.user.exception.UserErrorCode;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UserSettingsService {

    private final UserRepository userRepository;

    // ✅ 알림 On/Off 변경
    public void updateAlarmSetting(Long userId, boolean alarmEnabled) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new OILException(UserErrorCode.USER_NOT_FOUND));

        user.updateAlarm(alarmEnabled);
    }

    // ✅ 미션 받을 시간 변경
    public void updateMissionReceiveTime(Long userId, LocalTime time) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new OILException(UserErrorCode.USER_NOT_FOUND));

        user.updateMissionTime(time);
    }
}
