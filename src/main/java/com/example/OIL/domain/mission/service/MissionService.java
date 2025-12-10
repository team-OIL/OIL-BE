package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.Mission;
import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.MissionRepository;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.mission.exception.MissionErrorCode;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;

    public Mission getRandomMission() {
        return missionRepository.findRandomMission()
                .orElseThrow(() -> new OILException(MissionErrorCode.RANDOM_MISSION_NOT_FOUND));
    }
}
