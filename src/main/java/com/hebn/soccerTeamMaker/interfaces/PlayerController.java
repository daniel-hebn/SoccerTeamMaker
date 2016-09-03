package com.hebn.soccerTeamMaker.interfaces;

import com.hebn.soccerTeamMaker.application.PlayerService;
import com.hebn.soccerTeamMaker.domain.Player;
import com.hebn.soccerTeamMaker.infrastructure.util.LoggingUtils;
import com.hebn.soccerTeamMaker.interfaces.convert.PlayerConverter;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by greg.lee on 2016. 8. 23..
 */
@Slf4j
@RequestMapping("/players")
@Controller
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerConverter converter;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("playerList", playerService.findByTeamId(1L));
        return "player/list";
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.GET)
    public String detail(@PathVariable(value = "playerId") Long playerId, Model model) {
        model.addAttribute("player", playerService.findBy(playerId));
        return "player/detail";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String addPlayerView(Model model) {
        model.addAttribute("levelList", Player.Level.values());
        model.addAttribute("positionList", Player.Position.values());
        return "player/form";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public ResponseEntity create(@RequestBody PlayerDto.Create createParam) {
        try {
            Player player = converter.convert(createParam);
            playerService.save(player);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            String rootCauseExceptionMessage = LoggingUtils.errorLogging(log, e, createParam);
            return new ResponseEntity(rootCauseExceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ResponseBody
    @RequestMapping(value = "/{playerId}", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
    public ResponseEntity update(@PathVariable(value = "playerId") Long playerId,
                                 @RequestBody PlayerDto.Update updateParam) {
        try {
            Player player = playerService.findBy(playerId);
            bindingPlayerFromDto(player, updateParam);
            playerService.save(player);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            String rootCauseExceptionMessage = LoggingUtils.errorLogging(log, e, updateParam);
            return new ResponseEntity(rootCauseExceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private void bindingPlayerFromDto(Player player, PlayerDto.Update updateParam) {
        player.setAge(updateParam.getAge());
        player.setBackNumber(updateParam.getBackNumber());
        player.setPosition(Player.Position.valueOf(updateParam.getPosition()));
    }

    @ResponseBody
    @RequestMapping(value = "/{playerId}/usable", method = RequestMethod.PUT, produces = "application/json; charset=utf8")
    public ResponseEntity updateUsable(@PathVariable(value = "playerId") Long playerId,
                                       @RequestBody PlayerDto.Update updateParam) {
        try {
            Player player = playerService.findBy(playerId);
            player.setUsable(updateParam.getUsable());
            playerService.save(player);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            String rootCauseExceptionMessage = LoggingUtils.errorLogging(log, e, updateParam);
            return new ResponseEntity(rootCauseExceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
