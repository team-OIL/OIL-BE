package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.mission.presentation.dto.response.MissionHistoryItemResponse;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.service.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompleteMissionListService {

    private final UserMissionRepository userMissionRepository;
    private final UserFacade userFacade;

    // 완료한 미션들의 목록 (제목만 반환)
    public List<MissionHistoryItemResponse> execute() {

        User currentUser = userFacade.getCurrentUser();

        List<UserMission> missions =
                userMissionRepository.findByUserIdAndIsCompletedOrderByCompletedAtDesc(currentUser.getId(), true);

        return missions.stream()
                .map(m -> new MissionHistoryItemResponse(
                        m.getId(),
                        m.getMission().getContent()
                ))
                .toList();
    }
}
