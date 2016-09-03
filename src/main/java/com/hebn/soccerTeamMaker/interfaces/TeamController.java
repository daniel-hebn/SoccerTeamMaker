package com.hebn.soccerTeamMaker.interfaces;

import com.hebn.soccerTeamMaker.application.TeamBuilderService;
import com.hebn.soccerTeamMaker.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String teamMemberTableView() {
        return "test";
    }

    @ResponseBody
    @RequestMapping(value = "/generator", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public String teamBuilding() {
        Map<String, List<Player>> teamListMap = teamBuilderService.teamBuilding(1L);

        List<Player> firstTeamList = teamListMap.get(TeamBuilderService.FIRST_TEAM_NAME);
        List<Player> secondTeamList = teamListMap.get(TeamBuilderService.SECOND_TEAM_NAME);

        // TODO - 임시 코드
        Map<Player.Position, List<Player>> firstTeamListGroupingByPosition
                = firstTeamList.stream().collect(groupingBy(Player::getPosition));
        Map<Player.Position, List<Player>> secondTeamListGroupingByPosition
                = secondTeamList.stream().collect(groupingBy(Player::getPosition));

        StringBuilder sb = new StringBuilder();
        sb.append(">> A-Team PlayerList");

        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Forward : ");
        sb.append(System.lineSeparator());

        List<Player> firstForwardList = firstTeamListGroupingByPosition.get(Player.Position.FORWARD);
        sb.append(firstForwardList.stream().map(p -> p.getName()).collect(joining(", ")));
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Midfielder : ");
        sb.append(System.lineSeparator());

        List<Player> firstMidfielderList = firstTeamListGroupingByPosition.get(Player.Position.MIDFIELDER);
        sb.append(firstMidfielderList.stream().map(p -> p.getName()).collect(joining(", ")));
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Defender : ");
        sb.append(System.lineSeparator());

        List<Player> firstDefenderList = firstTeamListGroupingByPosition.get(Player.Position.DEFENDER);
        sb.append(firstDefenderList.stream().map(p -> p.getName()).collect(joining(", ")));
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("GoalKeeper : ");
        sb.append(System.lineSeparator());

        List<Player> firstGoalkeeperList = firstTeamListGroupingByPosition.get(Player.Position.GOALKEEPER);
        sb.append(firstGoalkeeperList.stream().map(p -> p.getName()).collect(joining(", ")));
        sb.append(System.lineSeparator());

        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append(">> B-Team PlayerList");

        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Forward : ");
        sb.append(System.lineSeparator());

        List<Player> secondForwardList = secondTeamListGroupingByPosition.get(Player.Position.FORWARD);
        sb.append(secondForwardList.stream().map(p -> p.getName()).collect(joining(", ")));
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Midfielder : ");
        sb.append(System.lineSeparator());

        List<Player> secondMidfielderList = secondTeamListGroupingByPosition.get(Player.Position.MIDFIELDER);
        sb.append(secondMidfielderList.stream().map(p -> p.getName()).collect(joining(", ")));
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Defender : ");
        sb.append(System.lineSeparator());

        List<Player> secondDefenderList = secondTeamListGroupingByPosition.get(Player.Position.DEFENDER);
        sb.append(secondDefenderList.stream().map(p -> p.getName()).collect(joining(", ")));
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("GoalKeeper : ");
        sb.append(System.lineSeparator());

        List<Player> secondGoalkeeperList = secondTeamListGroupingByPosition.get(Player.Position.GOALKEEPER);
        sb.append(secondGoalkeeperList.stream().map(p -> p.getName()).collect(joining(", ")));
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());

        return sb.toString();
    }


//    @ResponseBody
//    @RequestMapping(value = "/memberTables", method = RequestMethod.GET)
//    public ResponseEntity teamMemberList() {
//        Map<String, List<Player>> teamListMap = teamBuilderService.teamBuilding(1L);
//
//        List<Player> firstTeamList = teamListMap.get(TeamBuilderService.FIRST_TEAM_NAME);
//        List<Player> secondTeamList = teamListMap.get(TeamBuilderService.SECOND_TEAM_NAME);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
