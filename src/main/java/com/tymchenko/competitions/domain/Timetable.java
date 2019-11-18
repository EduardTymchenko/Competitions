package com.tymchenko.competitions.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
public class Timetable {
    private List<Team> teamList;
    private List<Round> timeTable;

    public Timetable(int amountTeams) {
        this.teamList = genTeamList(amountTeams);
        this.timeTable = generateTimetable();
    }

    public Timetable(List<Team> teamList) {
        this.teamList = teamList;
    }

    private List<Team> genTeamList(int amount) {
        List<Team> newTeamList = new ArrayList<>();
        String defaultName = "Team";
        for (int i = 1; i <= amount; i++) {
            Team team = new Team(defaultName + i);
            team.setNuberOfToss(i);
            newTeamList.add(team);
        }
        return sotrByToss(newTeamList);
    }

    private List<Team> sotrByToss(List<Team> listTeams) {
        Collections.sort(listTeams, new Comparator<Team>() {
            public int compare(Team team1, Team team2) {
                return team1.getNuberOfToss() - (team2.getNuberOfToss());
            }
        });
        return listTeams;
    }

    private List<Round> generateTimetable() {
        int amount = teamList.size();
        List<Round> teamTable = new ArrayList<>();
        List<Round> circleOne = new ArrayList<>();
        List<Round> circleTwo = new ArrayList<>();
        List<Team> sortTeamList = new ArrayList<>(teamList);
        if (amount % 2 != 0){
            Team zero = new Team("zero");
            zero.setNuberOfToss(0);
            sortTeamList.add(zero);
            amount++;
        }
        for (int n = 0; n < amount - 1; n++) {
            Round roundHome = new Round();
            Round roundGuests = new Round();
            int numberFirstTeam = 0;
            int numberSecondTeam = amount - 1;
            for (int i = 0; i < amount / 2; i++) {
                Team teamFirst = sortTeamList.get(numberFirstTeam);
                Team teamSecond = sortTeamList.get(numberSecondTeam);
                if (teamFirst.getNuberOfToss() != 0 && teamSecond.getNuberOfToss() != 0){
                roundHome.addMatch(new Match(teamFirst, teamSecond));
                roundGuests.addMatch(new Match(teamSecond, teamFirst));
                }
                numberFirstTeam++;
                numberSecondTeam--;
            }
            circleOne.add(roundHome);
            circleTwo.add(roundGuests);
            sortTeamList.add(sortTeamList.remove(1));
        }
        teamTable.addAll(circleOne);
        teamTable.addAll(circleTwo);
        return teamTable;
    }

}
