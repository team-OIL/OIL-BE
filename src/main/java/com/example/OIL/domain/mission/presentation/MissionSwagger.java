package com.example.OIL.domain.mission.presentation;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import com.example.OIL.domain.mission.presentation.dto.request.MissionCompleteRequest;
import com.example.OIL.domain.mission.presentation.dto.response.MissionDetailResponse;
import com.example.OIL.domain.mission.presentation.dto.response.MissionHistoryItemResponse;
import com.example.OIL.domain.mission.presentation.dto.response.UserMissionResponse;
import com.example.OIL.global.security.OILUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MissionSwagger {

    @Operation(summary = "오늘의 미션 조회")
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = UserMission.class))
    )
    UserMissionResponse getTodayMission();

    @Operation(summary = "미션 완료")
    @ApiResponse(responseCode = "200", description = "완료 성공")
    void completeMission(
            @Parameter(description = "UserMission ID", required = true)
            @PathVariable Long missionId,

            @Parameter(description = "미션 완료 요청 DTO")
            @RequestPart MissionCompleteRequest request

//            @Parameter(
//                    description = "이미지 파일(Optional)",
//                    content = @Content(
//                            mediaType = "application/octet-stream",
//                            schema = @Schema(type = "string", format = "binary")
//                    )
//            )
//            @RequestPart(required = false) MultipartFile file
    );

    @Operation(summary = "완료한 미션 목록 조회")
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = MissionHistoryItemResponse.class)
                    )
            )
    )
    List<MissionHistoryItemResponse> getCompletedList();

    @Operation(summary = "미션 상세 조회")
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = MissionDetailResponse.class))
    )
    MissionDetailResponse getMissionDetail(
            @Parameter(description = "UserMission ID", required = true)
            @PathVariable Long userMissionId
    );
}
