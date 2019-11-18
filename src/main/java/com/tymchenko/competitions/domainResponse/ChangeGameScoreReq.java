package com.tymchenko.competitions.domainResponse;

import lombok.Data;

@Data
public class ChangeGameScoreReq {
    private String nameTournament;
    private String nameTeam1;
    private int goalsTeam1;
    private String nameTeam2;
    private int goalsTeam2;
}
