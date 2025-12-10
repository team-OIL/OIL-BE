package com.example.OIL.global.scheduler;

import com.example.OIL.domain.mission.service.UserMissionService;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MissionScheduler {
    private final UserRepository userRepository;
    private final UserMissionService userMissionService;

    // 매 1분마다 실행됨
    @Scheduled(cron = "0 * * * * *")
    public void assignUserMissions() {

        LocalTime now = LocalTime.now().withSecond(0).withNano(0);  // 초 단위 제거 (13:00 형태)

        // 이 시간에 미션 받기로 설정한 유저 검색
        List<User> users = userRepository.findByMissiontime(now);

        for (User user : users) {
            userMissionService.createDailyMission(user);
        }
    }
}
