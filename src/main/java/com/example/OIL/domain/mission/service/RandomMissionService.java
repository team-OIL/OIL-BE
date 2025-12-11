package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.Mission;
import com.example.OIL.domain.mission.domain.repository.MissionRepository;
import com.example.OIL.domain.mission.exception.MissionErrorCode;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomMissionService {
    private final MissionRepository missionRepository;

    public Mission execute() {
        return missionRepository.findRandomMission()
                .orElseThrow(() -> new OILException(MissionErrorCode.RANDOM_MISSION_NOT_FOUND));
    }
}
