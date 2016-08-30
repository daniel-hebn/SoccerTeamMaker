package com.hebn.soccerTeamMaker.interfaces;

import com.hebn.soccerTeamMaker.application.TeamBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by greg.lee on 2016. 8. 25..
 */
@RestController
public class HelloController {

    @Autowired
    private TeamBuilderService teamBuilderService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/test")
    public String test() {
        teamBuilderService.teamBuilding(1L);
        return "test";
    }
}
