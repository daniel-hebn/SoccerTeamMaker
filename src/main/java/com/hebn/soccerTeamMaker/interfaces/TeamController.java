package com.hebn.soccerTeamMaker.interfaces;

import com.google.common.collect.Maps;
import com.hebn.soccerTeamMaker.application.TeamBuilderService;
import com.hebn.soccerTeamMaker.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
        setPlayerListByPositionInto(positionMemberMap, firstTeamListGroupingByPosition, Player.Position.FORWARD, "firstForwardList");
        setPlayerListByPositionInto(positionMemberMap, secondTeamListGroupingByPosition, Player.Position.FORWARD, "secondForwardList");

        // NOTE: MIDFIELDER member joining
        setPlayerListByPositionInto(positionMemberMap, firstTeamListGroupingByPosition, Player.Position.MIDFIELDER, "firstMidfielderList");
        setPlayerListByPositionInto(positionMemberMap, secondTeamListGroupingByPosition, Player.Position.MIDFIELDER, "secondMidfielderList");

        // NOTE: MIDFIELDER member joining
        setPlayerListByPositionInto(positionMemberMap, firstTeamListGroupingByPosition, Player.Position.DEFENDER, "firstDefenderList");
        setPlayerListByPositionInto(positionMemberMap, secondTeamListGroupingByPosition, Player.Position.DEFENDER, "secondDefenderList");

        // NOTE: GOALKEEPER member joining
        setPlayerListByPositionInto(positionMemberMap, firstTeamListGroupingByPosition, Player.Position.GOALKEEPER, "firstGoalkeeperList");
        setPlayerListByPositionInto(positionMemberMap, secondTeamListGroupingByPosition, Player.Position.GOALKEEPER, "secondGoalkeeperList");

        return new ResponseEntity(positionMemberMap, HttpStatus.OK);
    }

    private void setPlayerListByPositionInto(Map<String, String> positionMemberMap,
                                             Map<Player.Position, List<Player>> teamListGroupingByPosition,
                                             Player.Position position, String playerPositionListName) {
        List<Player> playerList = teamListGroupingByPosition.get(position);
        if (CollectionUtils.isEmpty(playerList)) {
            positionMemberMap.put(playerPositionListName, "");
        } else {
            positionMemberMap.put(playerPositionListName, playerList.stream().map(p -> p.getName()).collect(joining(", ")));
        }
    }
}
