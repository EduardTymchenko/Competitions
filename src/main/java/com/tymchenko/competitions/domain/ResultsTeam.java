package com.tymchenko.competitions.domain;

public class ResultsTeam {
    private String nameTeam;
    private int matchesPlayed;
    private int wins;
    private int deadHeat;
    private int losing;
    private int total;

    public ResultsTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getDeadHeat() {
        return deadHeat;
    }

    public int getLosing() {
        return losing;
    }

    public int getTotal() {
        return total;
    }

    public void addWins(int nuberWins) {
        wins = wins + nuberWins;
        countFieldValues();
    }

    public void addDeadHeat(int nuberDeadHeat) {
        deadHeat = deadHeat + nuberDeadHeat;
        countFieldValues();
    }

    public void addLosing(int nuberLosing) {
        losing = losing + nuberLosing;
        countFieldValues();
    }

    private int countingMatchesPlayed() {
        return wins + deadHeat + losing;
    }

    private int countingTotal() {
        int winsNumber = 3;
        int deadHeatNumber = 1;
        int losingNumber = 0;
        return wins * winsNumber + deadHeat * deadHeatNumber + losing * losingNumber;
    }

    private void countFieldValues() {
        matchesPlayed = countingMatchesPlayed();
        total = countingTotal();
    }
}
