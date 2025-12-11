package com.example.OIL.domain.mission.presentation;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.presentation.dto.request.MissionCompleteRequest;
import com.example.OIL.domain.mission.presentation.dto.response.MissionDetailResponse;
import com.example.OIL.domain.mission.presentation.dto.response.MissionHistoryItemResponse;
import com.example.OIL.domain.mission.service.CompleteMissionListService;
import com.example.OIL.domain.mission.service.CompleteMissionService;
import com.example.OIL.domain.mission.service.MissionDetailService;
import com.example.OIL.domain.mission.service.TodayMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController implements MissionSwagger {

    private final TodayMissionService todayMissionService;
    private final CompleteMissionListService completeMissionListService;
    private final MissionDetailService missionDetailService;
    private final CompleteMissionService completeMissionService;
    /**
     * 📌 오늘의 미션 조회 (또는 밀린 미션 조회)
     * - 해당 유저의 완료되지 않은 미션 중 가장 오래된 1개 반환
     * - 밀린 미션이 있으면 밀린 미션이 먼저 뜨고
     * - 밀린 미션이 없으면 오늘 생성된 미션이 뜬다.
     */
    @Override
    @GetMapping("/today")
    public UserMission getTodayMission(@RequestParam Long userId) {
        return todayMissionService.execute(userId);
    }


    /**
     * 📌 미션 완료 처리 API
     * - 메시지(text)와 이미지는 둘 다 optional
     * - 미션 완료 시간이 자동으로 저장됨
     */
    @Override
    @PostMapping("/{missionId}/complete")
    public void completeMission(
            @PathVariable Long missionId,
            @RequestPart MissionCompleteRequest request,
            @RequestPart(required = false) MultipartFile file
    ) {
        completeMissionService.execute(missionId, request, file);

    }

    /**
     * 📌 완료한 미션 목록 조회
     * - 제목만 반환
     */
    @GetMapping("/completed")
    public List<MissionHistoryItemResponse> getCompletedList(@RequestParam Long userId) {
        return completeMissionListService.execute(userId);
    }


    /**
     * 📌 특정 미션 상세 조회
     * - 제목, 메시지, 이미지, 완료 시간
     */
    @GetMapping("/{userMissionId}")
    public MissionDetailResponse getMissionDetail(@PathVariable Long userMissionId) {
        return missionDetailService.execute(userMissionId);
    }


}