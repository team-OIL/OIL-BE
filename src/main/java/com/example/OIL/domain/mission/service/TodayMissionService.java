package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.mission.exception.MissionErrorCode;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodayMissionService {

    private final UserMissionRepository userMissionRepository;
    // 밀린 미션 포함 가장 오래된 미완료 미션 반환
    public UserMission execute(Long userId) {
        return userMissionRepository.findOldestNotCompleted(userId)
                .orElseThrow(() -> new OILException(MissionErrorCode.MISSION_DATA_EMPTY));
    }
}
