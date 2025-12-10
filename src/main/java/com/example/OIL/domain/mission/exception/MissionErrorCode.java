package com.example.OIL.domain.mission.exception;

import com.example.OIL.global.error.exception.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements ErrorProperty {

    RANDOM_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "RANDOM_MISSION_NOT_FOUND", "랜덤 미션이 존재하지 않습니다."),
    MISSION_DATA_EMPTY(HttpStatus.NOT_FOUND, "MISSION_DATA_EMPTY", "미션 데이터가 없습니다."),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION_NOT_FOUND", "미션이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
