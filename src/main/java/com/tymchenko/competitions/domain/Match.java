package com.tymchenko.competitions.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Match {
    private Team teamFirst;
    private Team teamSecond;
@JsonIgnore
    private int goalsScoredFirst;
    @JsonIgnore
    private int goalsScoredSecond;

    public Match(Team teamFirst, Team teamSecond) {
        this.teamFirst = teamFirst;
        this.teamSecond = teamSecond;
        this.goalsScoredFirst = -1;
        this.goalsScoredSecond = -1;
    }


}
