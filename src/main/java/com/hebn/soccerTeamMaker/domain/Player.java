package com.hebn.soccerTeamMaker.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by greg.lee on 2016. 8. 23..
 */
@Entity
@Getter
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private Integer backNumber;

    private Position position;



    public enum Position {


        Forward, Midfielder, Defender, Goalkeeper;
    }
}
