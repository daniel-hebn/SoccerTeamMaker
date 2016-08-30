package com.hebn.soccerTeamMaker.application;

import com.google.common.collect.Maps;
import com.hebn.soccerTeamMaker.domain.Player;
import com.hebn.soccerTeamMaker.domain.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
@Slf4j
@Service
public class TeamBuilderServiceImpl implements TeamBuilderService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamRepository teamRepository;

    private Comparator<Player> orderByLevel = (p1, p2) -> p1.getLevel().compareTo(p2.getLevel());
    private Comparator<Player> orderByPosition = (p1, p2) -> p1.getPosition().compareTo(p2.getPosition());

    @Override
    public void teamBuilding(Long teamId) {
        List<Player> playerList = playerService.findByTeamId(teamId);
        List<Player> orderedPlayerList
                = playerList.stream().sorted(orderByLevel.thenComparing(orderByPosition)).collect(Collectors.toList());

        // TODO
        orderedPlayerList.stream().forEach(player -> System.out.println(player.getName()));

    }

}
