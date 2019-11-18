package com.tymchenko.competitions.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Round {
    private List<Match> teamsListInRound = new ArrayList<>();

    public void addMatch(Match match){
        teamsListInRound.add(match);
    }
}
