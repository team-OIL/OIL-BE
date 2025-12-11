package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.mission.exception.MissionErrorCode;
import com.example.OIL.domain.notification.service.NotificationFacade;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.domain.user.exception.UserErrorCode;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodayMissionService {

    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    private final NotificationFacade notificationFacade;

    // 밀린 미션 포함 가장 오래된 미완료 미션 반환
    public UserMission execute(Long userId) {

        // 1) 미완료된 미션 개수 확인
        long notCompletedCount = userMissionRepository.countByUserIdAndIsCompletedFalse(userId);

        // 2) 가장 오래된 미완료 미션 조회
        UserMission mission = userMissionRepository.findOldestNotCompleted(userId)
                .orElseThrow(() -> new OILException(MissionErrorCode.MISSION_DATA_EMPTY));

        // 3) 밀린 미션이 있는 경우 (2개 이상이면 이전 것도 남아있는 상태)
        if (notCompletedCount > 1) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new OILException(UserErrorCode.USER_NOT_FOUND));

            if (user.isAlarmEnabled()) {
                notificationFacade.notify(
                        user,
                        "밀린 미션이 있어요!",
                        "먼저 이전 미션부터 해결해 주세요."
                );
            }
        }

        return mission;

    }
}
