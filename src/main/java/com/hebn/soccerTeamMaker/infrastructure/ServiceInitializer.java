package com.hebn.soccerTeamMaker.infrastructure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by coupang on 2016. 8. 1..
 */
@Import(PersistenceInitializer.class)
@Configuration
@ComponentScan("com.hebn.soccerTeamMaker.application")
public class ServiceInitializer {

}
