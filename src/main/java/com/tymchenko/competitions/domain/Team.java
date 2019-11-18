package com.tymchenko.competitions.domain;


public class Team {
    private String nameTeam;
    private int nuberOfToss;

    public Team(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public int getNuberOfToss() {
        return nuberOfToss;
    }

    public void setNuberOfToss(int nuberOfToss) {
        this.nuberOfToss = nuberOfToss;
    }

    @Override
    public String toString() {
        return "Team{" +
                "nameTeam='" + nameTeam + '\'' +
                ", nuberOfToss=" + nuberOfToss +
                '}';
    }
}
