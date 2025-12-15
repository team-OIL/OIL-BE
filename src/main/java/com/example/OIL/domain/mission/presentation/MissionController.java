package com.example.OIL.domain.mission.presentation;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.presentation.dto.request.MissionCompleteRequest;
import com.example.OIL.domain.mission.presentation.dto.response.MissionDetailResponse;
import com.example.OIL.domain.mission.presentation.dto.response.MissionHistoryItemResponse;
import com.example.OIL.domain.mission.presentation.dto.response.UserMissionResponse;
import com.example.OIL.domain.mission.service.CompleteMissionListService;
import com.example.OIL.domain.mission.service.CompleteMissionService;
import com.example.OIL.domain.mission.service.MissionDetailService;
import com.example.OIL.domain.mission.service.TodayMissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController implements MissionSwagger {

    private final TodayMissionService todayMissionService;
    private final CompleteMissionListService completeMissionListService;
    private final MissionDetailService missionDetailService;
    private final CompleteMissionService completeMissionService;

    //유저의 오늘의 미션(밀린 미션 조회)
    //완료되지 않은 미션중 가장 오래된 1개 반환(밀린거 있으면 밀린거 먼저뜸)
    @Override
    @GetMapping("/today")
    public UserMissionResponse getTodayMission() {
        return todayMissionService.execute();
    }


    //미션 완료 이후 이미지, 텍스트 저장
    @Override
    @PostMapping("/{mission-id}/complete")
    public void completeMission(
            @PathVariable("mission-id") Long missionId,
            @ModelAttribute @Valid MissionCompleteRequest request

    ) {
        completeMissionService.execute(missionId, request);

    }

    //완료한 미션 목록
    @GetMapping("/completed")
    public List<MissionHistoryItemResponse> getCompletedList() {
        return completeMissionListService.execute();
    }


    //특정 미션 상세 조회 - 제목, 이미지, 메세지, 완료 시간
    @GetMapping("/{userMissionId}")
    public MissionDetailResponse getMissionDetail(@PathVariable Long userMissionId) {
        return missionDetailService.execute(userMissionId);
    }


}