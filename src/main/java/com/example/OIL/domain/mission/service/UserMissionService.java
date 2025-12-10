package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.Mission;
import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.mission.exception.MissionErrorCode;
import com.example.OIL.domain.mission.presentation.dto.request.MissionCompleteRequest;
import com.example.OIL.domain.user.domain.entity.User;
import com.example.OIL.domain.user.domain.repository.UserRepository;
import com.example.OIL.global.error.exception.OILException;
import com.example.OIL.global.s3.AwsS3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMissionService {
    private final MissionService missionService;
    private final UserMissionRepository userMissionRepository;
    private final AwsS3UploadService awsS3UploadService;

    // 매일 새로운 UserMission 생성
    public void createDailyMission(User user) {
        LocalDate today = LocalDate.now();

        // 이미 생성된 미션이 있으면 생성 X
        if (userMissionRepository.existsByUserIdAndAssignedDate(user.getId(), today)) {
            return;
        }

        Mission mission = missionService.getRandomMission();

        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .assignedDate(today) // 날짜 기반
                .build();

        userMissionRepository.save(userMission);
    }

    // 밀린 미션 포함 가장 오래된 미완료 미션 반환
    public UserMission getTodayMission(Long userId) {
        return userMissionRepository.findOldestNotCompleted(userId)
                .orElseThrow(() -> new OILException(MissionErrorCode.MISSION_DATA_EMPTY));
    }

    // 미션 완료 처리
    public void completeMission(
            Long userMissionId,
            MissionCompleteRequest request,
            MultipartFile file
    ) {

        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new OILException(MissionErrorCode.MISSION_NOT_FOUND));

        // 이미지 업로드 처리
        String imageUrl = request.imgUrl(); // 기본값 유지 (이미 값이 있을 수도 있음)

        if (file != null && !file.isEmpty()) {
            imageUrl = awsS3UploadService.upload(file);
        }

        // DTO 를 새로운 값(S3 URL 포함)으로 재생성하여 전달
        MissionCompleteRequest updatedRequest =
                new MissionCompleteRequest(request.text(), imageUrl);

        userMission.completeMission(updatedRequest);
    }

    public List<UserMission> getMissionHistory(Long userId) {
        return userMissionRepository
                .findByUserIdAndIsCompletedOrderByCompletedAtDesc(userId, true);
    }
}
