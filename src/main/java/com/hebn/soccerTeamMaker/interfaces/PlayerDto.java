package com.hebn.soccerTeamMaker.interfaces;

import lombok.Data;

/**
 * Created by greg.lee on 2016. 8. 29..
 */
public class PlayerDto {

    @Data
    public static class RetrieveCondition {
        private String level;
        private String name;
        private String position;
        private String teamName;
    }

    @Data
    public static class RetrieveResult {
        private String id;
        private String backNumber;
        private String level;
        private String name;
        private String position;
        private String teamName;
    }
}
