package com.hebn.soccerTeamMaker.application;

import com.hebn.soccerTeamMaker.domain.Player;
import com.hebn.soccerTeamMaker.domain.PlayerRepository;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by greg.lee on 2016. 8. 29..
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player findBy(Long id) {
        return playerRepository.findOne(id);
    }

    @Override
    public Page<Player> findByCondition(Predicate predicate, Pageable pageable) {
        return playerRepository.findAll(predicate, pageable);
    }

    @Override
    public Long countAll() {
        return playerRepository.count();
    }

    @Override
    public Long count(Predicate predicate) {
        return playerRepository.count(predicate);
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }
}
