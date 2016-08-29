package com.hebn.soccerTeamMaker.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by greg.lee on 2016. 8. 23..
 */
@Data
@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private Team parent;

    @OneToMany(mappedBy = "team")
    private List<Player> players;


}
