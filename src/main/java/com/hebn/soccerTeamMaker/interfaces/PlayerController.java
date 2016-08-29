package com.hebn.soccerTeamMaker.interfaces;

import com.hebn.soccerTeamMaker.application.PlayerService;
import com.hebn.soccerTeamMaker.interfaces.convert.PlayerConverter;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by greg.lee on 2016. 8. 23..
 */
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerConverter converter;

    @RequestMapping(method = RequestMethod.GET)
    public String list(@ModelAttribute(value = "param") PlayerDto.RetrieveCondition condition,
                       @PageableDefault(page = 0, value = 10) Pageable pageable, Model model) {

        Predicate predicate = converter.convert(condition);
        model.addAttribute("playerList", playerService.findByCondition(predicate, pageable));

        return "player/list";
    }
}
