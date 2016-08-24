package com.hebn.soccerTeamMaker.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by greg.lee on 2016. 8. 23..
 */
@Import(ServletInitializer.class)  // TODO - add SecurityInitializer
@Configuration
public class WebInitializer {

}
