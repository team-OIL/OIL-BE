package com.example.OIL.domain.user.presentation;

import com.example.OIL.domain.user.presentation.dto.request.UpdateAlarmSettingRequest;
import com.example.OIL.domain.user.presentation.dto.request.UpdateMissionTimeRequest;
import com.example.OIL.domain.user.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserSettingsController {

    private final UserSettingsService userSettingsService;

    /**
     * 📌 알림 On/Off 변경
     * PATCH /users/{userId}/settings/alarm
     */
    @PatchMapping("/{userId}/settings/alarm")
    public void updateAlarmSetting(
            @PathVariable Long userId,
            @RequestBody UpdateAlarmSettingRequest request
    ) {
        userSettingsService.updateAlarmSetting(userId, request.alarmEnabled());
    }

    /**
     * 📌 미션 받을 시간 변경
     * PATCH /users/{userId}/settings/mission-time
     * body 예: { "missionReceiveTime": "13:00" }
     */
    @PatchMapping("/{userId}/settings/mission-time")
    public void updateMissionReceiveTime(
            @PathVariable Long userId,
            @RequestBody UpdateMissionTimeRequest request
    ) {
        userSettingsService.updateMissionReceiveTime(userId, request.MissionTime());
    }
}
