package com.hebn.soccerTeamMaker.application;

import com.hebn.soccerTeamMaker.domain.PlayerRepository;
import com.hebn.soccerTeamMaker.domain.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
@Slf4j
@Service
public class TeamBuilderServiceImpl implements TeamBuilderService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;


    public void teamBuilding() {

        // 1. player 전체를 읽는다

        // 2. 읽은 player 를 분석한다.
        // 몇명인지 -> 팀 당 분배될 사람 수를 파악한다.

        // 상/공격   ->  하/수비 순으로 분배한다

    }

}
