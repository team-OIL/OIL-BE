package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.mission.exception.MissionErrorCode;
import com.example.OIL.domain.mission.presentation.dto.response.MissionDetailResponse;
import com.example.OIL.global.error.exception.OILException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionDetailService {

    private final UserMissionRepository userMissionRepository;
    // 특정 미션 상세 조회
    public MissionDetailResponse execute(Long userMissionId) {

        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new OILException(MissionErrorCode.MISSION_NOT_FOUND));

        return new MissionDetailResponse(
                userMission.getId(),
                userMission.getMission().getContent(),
                userMission.getResultText(),
                userMission.getResultImageUrl(),
                userMission.getCompletedAt()
        );
    }

}
