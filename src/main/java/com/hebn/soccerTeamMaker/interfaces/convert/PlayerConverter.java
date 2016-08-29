package com.hebn.soccerTeamMaker.interfaces.convert;

import com.hebn.soccerTeamMaker.domain.Player;
import com.hebn.soccerTeamMaker.domain.QPlayer;
import com.hebn.soccerTeamMaker.interfaces.PlayerDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by greg.lee on 2016. 8. 30..
 */
@Component
public class PlayerConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public PlayerConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Predicate convert(PlayerDto.RetrieveCondition condition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QPlayer player = QPlayer.player;

        if (StringUtils.isNotEmpty(condition.getLevel()))
            booleanBuilder.and(player.level.eq(Player.Level.valueOf(condition.getLevel())));
        if (StringUtils.isNotEmpty(condition.getName()))
            booleanBuilder.and(player.name.like("%" + condition.getName() + "%"));
        if (StringUtils.isNotEmpty(condition.getPosition()))
            booleanBuilder.and(player.position.eq(Player.Position.valueOf(condition.getPosition())));
        if (StringUtils.isNotEmpty(condition.getTeamName()))
            booleanBuilder.and(player.team.name.like("%" + condition.getTeamName() + "%"));

        return booleanBuilder.getValue();
    }

}
