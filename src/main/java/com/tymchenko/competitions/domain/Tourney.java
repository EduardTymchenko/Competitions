package com.tymchenko.competitions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.*;

@Data
@NoArgsConstructor
public class Tourney {
    @Id
    private String id;
    private String nameTourney;
    private List<Round> timetable;


    public Tourney(String nameTourney, int amountTeams) {
        this.nameTourney = nameTourney;
        this.timetable = new Timetable(amountTeams).getTimeTable();
    }

    public List<ResultsTeam> getResultTourney() {
        List<ResultsTeam> resultTable = new ArrayList<>();
        for (Round round : timetable) {
            for (Match match : round.getTeamsListInRound()) {
                ResultsTeam resultsTeamFirst = checkTeamInListResoult(match.getTeamFirst().getNameTeam(), resultTable);
                if (resultsTeamFirst == null) {
                    resultsTeamFirst = new ResultsTeam(match.getTeamFirst().getNameTeam());
                    resultTable.add(resultsTeamFirst);
                }
                ResultsTeam resultsTeamSecond = checkTeamInListResoult(match.getTeamSecond().getNameTeam(), resultTable);
                if (resultsTeamSecond == null) {
                    resultsTeamSecond = new ResultsTeam(match.getTeamSecond().getNameTeam());
                    resultTable.add(resultsTeamSecond);
                }
                if (!isPlay(match)) continue;
                int deltaGoal = match.getGoalsScoredFirst() - match.getGoalsScoredSecond();
                if (deltaGoal > 0) {
                    resultsTeamFirst.addWins(1);
                    resultsTeamSecond.addLosing(1);
                } else if (deltaGoal < 0) {
                    resultsTeamFirst.addLosing(1);
                    resultsTeamSecond.addWins(1);
                } else {
                    resultsTeamFirst.addDeadHeat(1);
                    resultsTeamSecond.addDeadHeat(1);
                }

            }
        }

        resultTable.sort((team1, team2) -> {
            if (team2.getTotal() - (team1.getTotal()) == 0) {
                return team1.getNameTeam().compareTo(team2.getNameTeam());
            } else {
                return team2.getTotal() - (team1.getTotal());
            }
        });

        return resultTable;
    }

    private boolean isPlay(Match match) {
        if (match.getGoalsScoredFirst() == -1 || match.getGoalsScoredSecond() == -1) return false;
        return true;
    }

    private ResultsTeam checkTeamInListResoult(String nameTeam, List<ResultsTeam> resultTable) {
        for (ResultsTeam iteam : resultTable) {
            if (iteam.getNameTeam().equals(nameTeam)) return iteam;
        }
        return null;
    }

    public List<Round> getListIsPlayMatchs(boolean isPlayMatches) {
        List<Round> listMatchs = new ArrayList<>();
        isPlayMatches = !isPlayMatches;
        for (Round round : timetable) {
            Round roundSort = new Round();
            for (Match match : round.getTeamsListInRound()) {
                if (isPlayMatches ^ isPlay(match)) roundSort.addMatch(match);
            }
            listMatchs.add(roundSort);
        }
        return listMatchs;
    }
}
