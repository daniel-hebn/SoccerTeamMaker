package com.hebn.soccerTeamMaker.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

}
