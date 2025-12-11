package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.Mission;
import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateDailyMissionService {

    private final UserMissionRepository userMissionRepository;
    private final RandomMissionService randomMissionService;

    // 매일 새로운 UserMission 생성
    public UserMission execute(User user) {
        LocalDate today = LocalDate.now();

        // 이미 생성된 미션이 있으면 생성 X
        if (userMissionRepository.existsByUserIdAndAssignedDate(user.getId(), today)) {
            return null;
        }

        Mission mission = randomMissionService.execute();

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .assignedDate(today) // 날짜 기반
                .build();

        return userMissionRepository.save(userMission);
    }
}
