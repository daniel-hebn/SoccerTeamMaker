package com.hebn.soccerTeamMaker.application;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hebn.soccerTeamMaker.domain.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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

    private Comparator<Player> orderByLevel = (p1, p2) -> p1.getLevel().compareTo(p2.getLevel());
    private Comparator<Player> orderByPosition = (p1, p2) -> p1.getPosition().compareTo(p2.getPosition());

    @Override
    public Map teamBuilding(Long teamId) {
        List<Player> playerList = playerService.findUsablePlayerByTeamId(teamId);
        List<Player> orderedPlayerList = playerList.stream()
                .sorted(orderByLevel.thenComparing(orderByPosition))
                .collect(toList());

        final List<Player> firstTeamList = Lists.newArrayList();
        final List<Player> secondTeamList = Lists.newArrayList();

        // Step 1
        // 레벨, 포지션 기준으로 ordering 후 팀 분배
        scatterPlayersInTwoTeam(orderedPlayerList, firstTeamList, secondTeamList);

        // Step 2
        // 랜덤 요소 추가 - 양 팀에서 같은 레벨과 포지션을 가지는 맴버 교환. 교환 맴버 수 / 포지션은 가능한 선에서 랜덤
        final Player.Level levelChangeSelector = getLevelChangeSelector();
        final Player.Position positionChangeSelector = getPositionChangeSelector();

        long firstTeamCountByPositionAndLevel = firstTeamList.stream().filter(getSameLevelAndPositionFilter(levelChangeSelector, positionChangeSelector)).count();
        long secondTeamCountByPositionAndLevel = secondTeamList.stream().filter(getSameLevelAndPositionFilter(levelChangeSelector, positionChangeSelector)).count();

        long changeableMaxCount = firstTeamCountByPositionAndLevel > secondTeamCountByPositionAndLevel ? secondTeamCountByPositionAndLevel : firstTeamCountByPositionAndLevel;
        int changeCountFactor = (int) ((Math.random() + 1) * changeableMaxCount); // NOTE: 교환 횟수 - 교환이 가능하다면 최소 한 번

        swapTeamPlayerBySameLevelAndSamePosition(firstTeamList, secondTeamList, levelChangeSelector, positionChangeSelector, changeCountFactor);

        // Step 3
        // HIGH Level 이면서 팀 맴버 숫자가 많을 경우, 해당 팀의 MIDDLE Level 1명을 반대 팀으로 이동 시키기
        transferAnyPositionMiddleLevelPlayerForBalancing(firstTeamList, secondTeamList);


        // Step 4
        // 포지션 불균형일 경우 - 각 팀에서 부족한 포지션을 가지는 동일 레벨 맴버 교환
        swapTeamPlayerBySameLevelWhenPositionUnBalanced(firstTeamList, secondTeamList);

        Map teamListMap = Maps.newHashMap();
        teamListMap.put(TeamBuilderService.FIRST_TEAM_NAME, firstTeamList);
        teamListMap.put(TeamBuilderService.SECOND_TEAM_NAME, secondTeamList);

        return teamListMap;
    }

    private void scatterPlayersInTwoTeam(List<Player> orderedPlayerList, List<Player> firstTeamList, List<Player> secondTeamList) {
        IntStream.range(0, orderedPlayerList.size()).forEach(idx -> {
            Player player = orderedPlayerList.get(idx);
            if (idx % 2 == 0) {
                firstTeamList.add(player);
            } else {
                secondTeamList.add(player);
            }
        });
    }

    private Player.Level getLevelChangeSelector() {
        final Player.Level levelChangeSelector;
        switch ((int) (Math.random() * 3)) {
            case 0:
                levelChangeSelector = Player.Level.HIGH;
                break;
            case 1:
                levelChangeSelector = Player.Level.MIDDLE;
                break;
            case 2:
                levelChangeSelector = Player.Level.LOW;
                break;
            default:
                levelChangeSelector = Player.Level.MIDDLE;
        }
        return levelChangeSelector;
    }


    private Player.Position getPositionChangeSelector() {
        final Player.Position positionChangeSelector;
        switch ((int) (Math.random() * 3)) {
            case 0:
                positionChangeSelector = Player.Position.FORWARD;
                break;
            case 1:
                positionChangeSelector = Player.Position.MIDFIELDER;
                break;
            case 2:
                positionChangeSelector = Player.Position.DEFENDER;
                break;
            default:
                positionChangeSelector = Player.Position.FORWARD;
        }
        return positionChangeSelector;
    }

    private void swapTeamPlayerBySameLevelAndSamePosition(List<Player> firstPlayerList, List<Player> secondPlayerList,
                                                          Player.Level level, Player.Position position, int count) {
        List<Player> firstSwapPlayerList = firstPlayerList.stream().filter(getSameLevelAndPositionFilter(level, position)).limit(count).collect(toList());
        List<Player> secondSwapPlayerList = secondPlayerList.stream().filter(getSameLevelAndPositionFilter(level, position)).limit(count).collect(toList());

        firstSwapPlayerList.stream().forEach(p -> {
            log.info("first p = {}", p.getName());
            secondPlayerList.add(p);
            firstPlayerList.remove(p);
        });

        secondSwapPlayerList.stream().forEach(p -> {
            log.info("second p = {}", p.getName());
            firstPlayerList.add(p);
            secondPlayerList.remove(p);
        });
    }

    private Predicate<Player> getSameLevelAndPositionFilter(Player.Level level, Player.Position position) {
        return p -> p.getLevel() == level && p.getPosition() == position;
    }

    // NOTE: 항상 firstTeam 맴버 수가 secondTeam 맴버보다 많거나 같다
    private void transferAnyPositionMiddleLevelPlayerForBalancing(List<Player> firstTeamList, List<Player> secondTeamList) {
        long firstTeamHighLevelMemberCount = firstTeamList.stream().filter(p -> p.getLevel() == Player.Level.HIGH).count();
        long secondTeamHighLevelMemberCount = secondTeamList.stream().filter(p -> p.getLevel() == Player.Level.HIGH).count();

        if (firstTeamList.size() > secondTeamList.size() && firstTeamHighLevelMemberCount > secondTeamHighLevelMemberCount) {
            Optional<Player> changeMiddleLevelPlayer
                    = firstTeamList.stream().filter(p -> p.getPosition() != Player.Position.GOALKEEPER && p.getLevel() == Player.Level.MIDDLE).findAny();

            if (changeMiddleLevelPlayer.isPresent()) {
                log.info(">>> transferAnyPositionMiddleLevelPlayerForBalancing = {}", changeMiddleLevelPlayer.get().getName());
                secondTeamList.add(changeMiddleLevelPlayer.get());
                firstTeamList.remove(changeMiddleLevelPlayer.get());
            }
        }

    }

    private void swapTeamPlayerBySameLevelWhenPositionUnBalanced(List<Player> firstTeamList, List<Player> secondTeamList) {
        Map<Player.Position, Long> firstTeamPositionCountingMap = firstTeamList.stream().filter(getFilterNotGoalKeeper()).collect(Collectors.groupingBy(Player::getPosition, Collectors.counting()));
        Map<Player.Position, Long> secondTeamPositionCountingMap = secondTeamList.stream().filter(getFilterNotGoalKeeper()).collect(Collectors.groupingBy(Player::getPosition, Collectors.counting()));

        Player.Position firstTeamMaxCountPosition = firstTeamPositionCountingMap.entrySet().stream().max((e1, e2) -> (int) (e1.getValue() - e2.getValue())).get().getKey();
        Player.Position secondTeamMaxCountPosition = secondTeamPositionCountingMap.entrySet().stream().max((e1, e2) -> (int) (e1.getValue() - e2.getValue())).get().getKey();

        Optional<Player> opFirstSwapPlayer = firstTeamList.stream().filter(p -> p.getPosition() == firstTeamMaxCountPosition).findAny();
        if (opFirstSwapPlayer.isPresent()) {
            Player firstSwapPlayer = opFirstSwapPlayer.get();
            secondTeamList.add(firstSwapPlayer);
            firstTeamList.remove(firstSwapPlayer);
        }

        Optional<Player> opSecondSwapPlayer = secondTeamList.stream().filter(p -> p.getPosition() == secondTeamMaxCountPosition && p.getLevel() == opFirstSwapPlayer.get().getLevel()).findAny();
        if (opSecondSwapPlayer.isPresent()) {
            Player secondSwapPlayer = opSecondSwapPlayer.get();
            firstTeamList.add(secondSwapPlayer);
            secondTeamList.remove(secondSwapPlayer);
        }

    }

    private Predicate<Player> getFilterNotGoalKeeper() {
        return p -> p.getPosition() != Player.Position.GOALKEEPER;
    }

}
