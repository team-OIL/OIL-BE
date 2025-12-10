package com.example.OIL.config;

import com.example.OIL.domain.mission.domain.entity.Mission;
import com.example.OIL.domain.mission.domain.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MissionDataInitializer implements ApplicationRunner {

    private final MissionRepository missionRepository;

    @Override
    public void run(ApplicationArguments args) {

        // 이미 DB에 미션이 있다면 초기화 안 함
        if (missionRepository.count() > 0) {
            return;
        }

        List<Mission> missions = List.of(
                createMission("10분 산책하기", 10),
                createMission("물 2잔 마시기", 5),
                createMission("책 10페이지 읽기", 15),
                createMission("심호흡 1분 하기", 1),
                createMission("집안 정리 5분 하기", 5),
                createMission("간단 스트레칭 하기", 5),
                createMission("오늘 하루 감사한 일 적어보기", 5),
                createMission("가벼운 운동 10분 하기", 10),
                createMission("일기 3줄 쓰기", 5),
                createMission("좋아하는 음악 듣기", 3)
        );

        missionRepository.saveAll(missions);
        //System.out.println("[MissionDataInitializer] 초기 미션 데이터 등록 완료!");
    }

    private Mission createMission(String content, int durationTime) {
        return Mission.builder()
                .content(content)
                .durationTime(durationTime)
                .build();
    }
}