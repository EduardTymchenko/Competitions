package com.tymchenko.competitions.service;

import com.tymchenko.competitions.domain.Round;
import com.tymchenko.competitions.domain.Tourney;

import java.util.List;

public interface TourneyService {
    List<Round> getTimeTableByNameTourneyAndAmount(String nameTourney, int amountTeams);
    Tourney getTourneyByName(String nameTourney);
    Tourney changeScore(String nameTourney, String nameTeam1, int goals1, String nameTeam2, int goals2);

}
