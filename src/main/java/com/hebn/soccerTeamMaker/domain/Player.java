package com.hebn.soccerTeamMaker.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

/**
 * Created by greg.lee on 2016. 8. 23..
 */
@Data
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer backNumber;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId")
    private Team team;

    public enum Position {
        FORWARD, MIDFIELDER, DEFENDER, GOALKEEPER;
    }

    public enum Level {
        HIGH, MIDDLE, LOW;
    }
}
