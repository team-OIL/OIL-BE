package com.example.OIL.domain.mission.service;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.domain.repository.UserMissionRepository;
import com.example.OIL.domain.mission.exception.MissionErrorCode;
import com.example.OIL.domain.mission.presentation.dto.request.MissionCompleteRequest;
import com.example.OIL.global.error.exception.OILException;
import com.example.OIL.global.s3.AwsS3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompleteMissionService {

    private final UserMissionRepository userMissionRepository;
    private final AwsS3UploadService awsS3UploadService;

    @Transactional
    public void execute(Long userMissionId, MissionCompleteRequest request) {

        UserMission userMission = userMissionRepository.findById(userMissionId)
                .orElseThrow(() -> new OILException(MissionErrorCode.MISSION_NOT_FOUND));

        // 1) 이미지 업로드 (Optional)
        String imageUrl = null;
        if (request.file() != null && !request.file().isEmpty()) {
            imageUrl = awsS3UploadService.upload(request.file());
        }

        // 2) 엔티티에 텍스트와 S3 URL 업데이트
        userMission.completeMission(request.resultText(), imageUrl);
    }

}
