package com.example.OIL.domain.mission.presentation;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.presentation.dto.request.MissionCompleteRequest;
import com.example.OIL.domain.mission.service.UserMissionService;
import com.example.OIL.global.s3.AwsS3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final UserMissionService userMissionService;
    private final AwsS3UploadService awsS3UploadService;
    /**
     * 📌 오늘의 미션 조회 (또는 밀린 미션 조회)
     * - 해당 유저의 완료되지 않은 미션 중 가장 오래된 1개 반환
     * - 밀린 미션이 있으면 밀린 미션이 먼저 뜨고
     * - 밀린 미션이 없으면 오늘 생성된 미션이 뜬다.
     */
    @GetMapping("/today")
    public UserMission getTodayMission(@RequestParam Long userId) {
        return userMissionService.getTodayMission(userId);
    }


    /**
     * 📌 미션 완료 처리 API
     * - 메시지(text)와 이미지 URL(imageUrl)은 둘 다 optional
     * - 미션 완료 시간이 자동으로 저장됨
     */
    @PostMapping("/{missionId}/complete")
    public void completeMission(
            @PathVariable Long missionId,
            @RequestPart MissionCompleteRequest request,
            @RequestPart(required = false) MultipartFile file
    ) {
        userMissionService.completeMission(missionId, request, file);

    }

    @GetMapping("/history")
    public List<UserMission> getHistory(@RequestParam Long userId) {
        return userMissionService.getMissionHistory(userId);
    }

}