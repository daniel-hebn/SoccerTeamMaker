package com.hebn.soccerTeamMaker.application;

import java.util.Map;

/**
 * Created by greg.lee on 2016. 8. 23..
 */

public interface TeamBuilderService {

    String FIRST_TEAM_NAME = "A-Team";
    String SECOND_TEAM_NAME = "B-Team";

    Map teamBuilding(Long teamId);
}
