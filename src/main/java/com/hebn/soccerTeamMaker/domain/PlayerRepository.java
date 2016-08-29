package com.hebn.soccerTeamMaker.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, QueryDslPredicateExecutor<Player> {

}
