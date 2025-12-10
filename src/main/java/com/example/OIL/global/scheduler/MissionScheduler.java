package com.example.OIL.global.scheduler;

import com.example.OIL.domain.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class MissionScheduler {
    private final MissionService missionService;

    // cron = "초 분 시 일 월 요일"
    // 매분 0초에 실행 (1분 주기)
    @Scheduled(cron = "0 * * * * *")
    public void scheduleMissionDistribution() {
        // 현재 시간을 분 단위까지만 가져옴 (초, 나노초 제거 -> 14:02:00)
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        // 서비스 호출
        missionService.distributeMissions(now);
    }
}
