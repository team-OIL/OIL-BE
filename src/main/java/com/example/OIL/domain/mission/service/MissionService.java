package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.Mission;
import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.MissionRepository;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MissionService {
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    @Transactional
    public void distributeMissions(LocalTime currentTime) {
        // 1. 현재 시간에 미션을 받아야 하는 유저 조회
        List<User> users = userRepository.findAllByMissionTime(currentTime);

        if (users.isEmpty()) {
            return; // 대상자가 없으면 종료
        }

        log.info("{} 시간에 해당하는 유저 {}명에게 미션 배정 시작", currentTime, users.size());

        for (User user : users) {
            // 2. 중복 방지: 오늘 이미 미션을 받았다면 패스
            if (userMissionRepository.existsByUserIdAndAssignedDate(user.getId(), LocalDate.now())) {
                continue;
            }

            // 3. 랜덤 미션 뽑기
            Mission randomMission = missionRepository.findRandomMission()
                    .orElseThrow(() -> new RuntimeException("등록된 미션이 없습니다."));

            // 4. 사용자에게 미션 할당 및 저장
            UserMission userMission = UserMission.builder()
                    .user(user)
                    .mission(randomMission)
                    .assignedDate(LocalDate.now())
                    .build();

            userMissionRepository.save(userMission);

            // TODO: 여기서 FCM(Firebase) 등을 통해 "미션이 도착했습니다!" 푸시 알림 전송
        }
    }
}
