package com.hebn.soccerTeamMaker.application;

import com.hebn.soccerTeamMaker.domain.Player;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
public interface PlayerService {

    Player findBy(Long id);

    Page<Player> findByCondition(Predicate predicate, Pageable pageable);

    Long countAll();

    Long count(Predicate predicate);

    Player save(Player player);

}
