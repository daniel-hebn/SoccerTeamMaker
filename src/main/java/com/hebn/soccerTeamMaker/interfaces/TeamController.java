package com.hebn.soccerTeamMaker.interfaces;

import com.google.common.collect.Maps;
import com.hebn.soccerTeamMaker.application.TeamBuilderService;
import com.hebn.soccerTeamMaker.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
@Slf4j
@Controller
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamBuilderService teamBuilderService;

    @RequestMapping(method = RequestMethod.GET)
    public String initView() {
        return "team/team-view";
    }

    @ResponseBody
    @RequestMapping(value = "/generator", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity teamBuilding() {
        Map<String, List<Player>> teamListMap = teamBuilderService.teamBuilding(1L);

        List<Player> firstTeamList = teamListMap.get(TeamBuilderService.FIRST_TEAM_NAME);
        List<Player> secondTeamList = teamListMap.get(TeamBuilderService.SECOND_TEAM_NAME);

        Map<Player.Position, List<Player>> firstTeamListGroupingByPosition
                = firstTeamList.stream().collect(groupingBy(Player::getPosition));
        Map<Player.Position, List<Player>> secondTeamListGroupingByPosition
                = secondTeamList.stream().collect(groupingBy(Player::getPosition));

        // 팀 맴버 분배 결과 생성
        Map<String, String> positionMemberMap = Maps.newHashMap();

        // NOTE: FORWARD member joining
        List<Player> firstForwardList = firstTeamListGroupingByPosition.get(Player.Position.FORWARD);
        positionMemberMap.put("firstForwardList", firstForwardList.stream().map(p -> p.getName()).collect(joining(", ")));

        List<Player> secondForwardList = secondTeamListGroupingByPosition.get(Player.Position.FORWARD);
        positionMemberMap.put("secondForwardList", secondForwardList.stream().map(p -> p.getName()).collect(joining(", ")));


        // NOTE: MIDFIELDER member joining
        List<Player> firstMidfielderList = firstTeamListGroupingByPosition.get(Player.Position.MIDFIELDER);
        positionMemberMap.put("firstMidfielderList", firstMidfielderList.stream().map(p -> p.getName()).collect(joining(", ")));

        List<Player> secondMidfielderList = secondTeamListGroupingByPosition.get(Player.Position.MIDFIELDER);
        positionMemberMap.put("secondMidfielderList", secondMidfielderList.stream().map(p -> p.getName()).collect(joining(", ")));


        // NOTE: MIDFIELDER member joining
        List<Player> firstDefenderList = firstTeamListGroupingByPosition.get(Player.Position.DEFENDER);
        positionMemberMap.put("firstDefenderList", firstDefenderList.stream().map(p -> p.getName()).collect(joining(", ")));

        List<Player> secondDefenderList = secondTeamListGroupingByPosition.get(Player.Position.DEFENDER);
        positionMemberMap.put("secondDefenderList", secondDefenderList.stream().map(p -> p.getName()).collect(joining(", ")));


        // NOTE: GOALKEEPER member joining
        List<Player> firstGoalkeeperList = firstTeamListGroupingByPosition.get(Player.Position.GOALKEEPER);
        positionMemberMap.put("firstGoalkeeperList", firstGoalkeeperList.stream().map(p -> p.getName()).collect(joining(", ")));

        List<Player> secondGoalkeeperList = secondTeamListGroupingByPosition.get(Player.Position.GOALKEEPER);
        positionMemberMap.put("secondGoalkeeperList", secondGoalkeeperList.stream().map(p -> p.getName()).collect(joining(", ")));

        return new ResponseEntity(positionMemberMap, HttpStatus.OK);
    }

}
