package com.hebn.soccerTeamMaker.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
@Repository
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

}
