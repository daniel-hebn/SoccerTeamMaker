package com.hebn.soccerTeamMaker.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by greg.lee on 2016. 8. 23..
 */
@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Player> players;


}
