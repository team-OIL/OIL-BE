package com.example.OIL.domain.user.presentation;

import com.example.OIL.domain.user.presentation.dto.request.UpdateAlarmSettingRequest;
import com.example.OIL.domain.user.presentation.dto.request.UpdateMissionTimeRequest;
import com.example.OIL.domain.user.service.UpdateAlarmService;
import com.example.OIL.domain.user.service.UpdateMissionReceiveTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserSettingsController {

    private final UpdateAlarmService updateAlarmService;
    private final UpdateMissionReceiveTimeService updateMissionReceiveTimeService;

    // 미션 받을 시간 변경
    @PatchMapping("/settings/mission-time")
    public void updateMissionReceiveTime(
            @RequestBody UpdateMissionTimeRequest request
    ) {
        updateMissionReceiveTimeService.execute(request.MissionTime());
    }

    //알림 on/of
    @PatchMapping("/settings/alarm")
    public void updateAlarmSetting(
            @RequestBody UpdateAlarmSettingRequest request
    ) {
        updateAlarmService.execute(request.alarmEnabled());
    }
}
