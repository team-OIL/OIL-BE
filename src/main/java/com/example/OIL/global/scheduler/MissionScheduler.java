package com.example.OIL.global.scheduler;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.service.CreateDailyMissionService;
import com.example.OIL.domain.notification.service.NotificationFacade;
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
    private final CreateDailyMissionService createDailyMissionService;
    private final NotificationFacade notificationFacade;

    // 매 1분마다 실행됨
    @Scheduled(cron = "0 * * * * *")
    public void assignUserMissions() {

        LocalTime now = LocalTime.now().withSecond(0).withNano(0);  // 초 단위 제거 (13:00 형태)

        // 이 시간에 미션 받기로 설정한 유저 검색
        List<User> users = userRepository.findByMissionTime(now);

        for (User user : users) {
            UserMission createdMission = createDailyMissionService.execute(user);

            // 오늘 미션이 새로 만들어진 경우에만
            if (createdMission != null && user.isAlarmEnabled()) {
                notificationFacade.notify(
                        user,
                        "오늘의 미션이 도착했어요!",
                        createdMission.getMission().getContent()
                );
            }
        }
    }
}
