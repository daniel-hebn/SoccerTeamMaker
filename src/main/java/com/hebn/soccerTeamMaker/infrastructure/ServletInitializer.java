package com.hebn.soccerTeamMaker.infrastructure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by coupang on 2016. 8. 1..
 */

@Import(ServiceInitializer.class)
@Configuration
@ComponentScan("com.hebn.soccerTeamMaker.interfaces")
@EnableWebMvc
public class ServletInitializer extends WebMvcConfigurerAdapter {

}
