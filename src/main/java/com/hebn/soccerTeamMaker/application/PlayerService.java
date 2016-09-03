package com.hebn.soccerTeamMaker.application;

import com.hebn.soccerTeamMaker.domain.Player;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
public interface PlayerService {

    Player findBy(Long id);

    List<Player> findByTeamId(Long teamId);

    List<Player> findUsablePlayerByTeamId(Long teamId);

    Page<Player> findByCondition(Predicate predicate, Pageable pageable);

    Long countAll();

    Long count(Predicate predicate);

    Player save(Player player);

}
