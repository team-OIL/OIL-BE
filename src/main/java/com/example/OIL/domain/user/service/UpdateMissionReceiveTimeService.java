package com.example.OIL.domain.user.service;

import com.example.OIL.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UpdateMissionReceiveTimeService {

    private final UserFacade userFacade;

    @Transactional
    public void execute(LocalTime time) {
        User currentUser = userFacade.getCurrentUser();

        currentUser.updateMissionTime(time);
    }
}
