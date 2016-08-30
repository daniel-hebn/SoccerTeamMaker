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
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

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
                = playerList.stream().sorted(orderByLevel.thenComparing(orderByPosition)).collect(toList());

        final Map<Integer, Player> idxPlayerMap = Maps.newHashMap();
        IntStream.range(0, playerList.size()).forEach(idx -> idxPlayerMap.put(idx, orderedPlayerList.get(idx)));

        // NOTE: ordered List 기반으로 - Map<Integer, Player> : idx, player
        idxPlayerMap.entrySet().stream().forEach(entry -> System.out.println(entry.getKey()));
        idxPlayerMap.entrySet().stream().forEach(entry -> System.out.println(entry.getValue().getName()));


        final Map<Long, Player> firstTeamMap = Maps.newHashMap();
        final Map<Long, Player> secondTeamMap = Maps.newHashMap();

        idxPlayerMap.entrySet().stream().filter(entry -> entry.getKey() % 2 == 0)
                .forEach(player -> {
                    Player p = player.getValue();
                    firstTeamMap.put(p.getId(), p);
                });

        idxPlayerMap.entrySet().stream().filter(entry -> entry.getKey() % 2 != 0)
                .forEach(player -> {
                    Player p = player.getValue();
                    secondTeamMap.put(p.getId(), p);
                });

        log.info(">>> 1");
        firstTeamMap.entrySet().stream().forEach(entry -> System.out.println(entry.getValue().getName()));
        log.info(">>> 2");
        secondTeamMap.entrySet().stream().forEach(entry -> System.out.println(entry.getValue().getName()));



        // TODO

    }

}
