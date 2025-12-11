package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.mission.presentation.dto.response.MissionHistoryItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompleteMissionListService {

    private final UserMissionRepository userMissionRepository;
    // 완료한 미션들의 목록 (제목만 반환)
    public List<MissionHistoryItemResponse> execute(Long userId) {

        List<UserMission> missions =
                userMissionRepository.findByUserIdAndIsCompletedOrderByCompletedAtDesc(userId, true);

        return missions.stream()
                .map(m -> new MissionHistoryItemResponse(
                        m.getId(),
                        m.getMission().getContent()
                ))
                .toList();
    }
}
