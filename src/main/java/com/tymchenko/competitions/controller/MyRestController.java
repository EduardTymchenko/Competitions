package com.tymchenko.competitions.controller;

import com.tymchenko.competitions.domain.Match;
import com.tymchenko.competitions.domain.ResultsTeam;
import com.tymchenko.competitions.domain.Round;
import com.tymchenko.competitions.domain.Tourney;
import com.tymchenko.competitions.domainResponse.AllDataResponse;
import com.tymchenko.competitions.domainResponse.ChangeGameScoreReq;
import com.tymchenko.competitions.service.TourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MyRestController {


    @Autowired
    private TourneyService tourneyService;

    @GetMapping("/timetable/{typeRespotse}")
    public ResponseEntity<?> getTimetable(@PathVariable String typeRespotse,
                                          @RequestParam("nameTournament") String nameTournament,
                                          @RequestParam("amountTeams") int amountTeams) {
        if (!typeRespotse.equals("html") && !typeRespotse.equals("json")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ошибка параметров запроса");
        if (amountTeams < 2)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Колличество команд должно быть больше 1");
        if (typeRespotse.equals("json")) {
            return new ResponseEntity<>(tourneyService.getTimeTableByNameTourneyAndAmount(nameTournament, amountTeams), HttpStatus.OK);
        } else {
            String htmlResp = genHtmlTimeTable(tourneyService.getTimeTableByNameTourneyAndAmount(nameTournament, amountTeams), false);
            return new ResponseEntity<>(htmlResp, HttpStatus.OK);
        }
    }


    @GetMapping("/allData/{typeRespotse}")
    public ResponseEntity<?> getAllData(@PathVariable String typeRespotse,
                                        @RequestParam("nameTournament") String nameTournament) {
        if (!typeRespotse.equals("html") && !typeRespotse.equals("json")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ошибка параметров запроса");
        Tourney tourney = tourneyService.getTourneyByName(nameTournament);
        if (typeRespotse.equals("json")) {
            AllDataResponse restAllData = new AllDataResponse();
            restAllData.setTimetable(tourney.getTimetable());
            restAllData.setTeamResultResposeList(tourney.getResultTourney());
            return new ResponseEntity<>(restAllData, HttpStatus.OK);
        } else {
            StringBuffer respHtml = new StringBuffer();
            respHtml.append(genHtmlTimeTable(tourney.getTimetable(), true))
                    .append("<br><br>")
                    .append(genHtmlResult(tourney.getResultTourney()));
            return new ResponseEntity<>(respHtml.toString(), HttpStatus.OK);
        }
    }


    @PostMapping("/changeScore")
    public ResponseEntity<?> changeGameScore(@ModelAttribute ChangeGameScoreReq req) {
        Tourney tourney = tourneyService.changeScore(req.getNameTournament(), req.getNameTeam1(), req.getGoalsTeam1(), req.getNameTeam2(), req.getGoalsTeam2());
        StringBuffer res = new StringBuffer();
        String listIsPlay = genHtmlTimeTable(tourney.getListIsPlayMatchs(true), true);
        String listIsNotPlay = genHtmlTimeTable(tourney.getListIsPlayMatchs(false), false);
        res.append(listIsPlay).append("<br><br>").append(listIsNotPlay);
        return new ResponseEntity<>(res.toString(), HttpStatus.OK);
    }

    private String genHtmlTimeTable(List<Round> timeTable, boolean isShowScore) {
        int roundNumber = 1;
        String trTeam;
        String trRound;
        if (isShowScore) {
            trTeam = "<tr><td></td><td></td><td></td><td></td></tr>";
            trRound = "<tr><td colspan=\"4\"></td></tr>";
        } else {
            trTeam = "<tr><td></td><td></td></tr>";
            trRound = "<tr><td colspan=\"2\"></td></tr>";
        }

        StringBuilder doc = new StringBuilder();
        doc.append("<table border=\"1\">");
        int lastIndex = trTeam.lastIndexOf("</td>");
        int step = 9;
        for (Round round : timeTable) {
            if (round.getTeamsListInRound().size() == 0) {
                roundNumber++;
                continue;
            }
            StringBuilder nameRoud = new StringBuilder();
            nameRoud.append(trRound).insert(20, "Round " + roundNumber);
            doc.append(nameRoud);
            for (Match match : round.getTeamsListInRound()) {
                StringBuilder matchLine = new StringBuilder();
                matchLine.append(trTeam);
                if (isShowScore) {
                    matchLine.insert(lastIndex, match.getGoalsScoredSecond());
                    matchLine.insert(lastIndex - step, match.getGoalsScoredFirst());
                    matchLine.insert(lastIndex - (2 * step), match.getTeamSecond().getNameTeam());
                    matchLine.insert(lastIndex - (3 * step), match.getTeamFirst().getNameTeam());

                } else {
                    matchLine.insert(lastIndex, match.getTeamSecond().getNameTeam());
                    matchLine.insert(lastIndex - step, match.getTeamFirst().getNameTeam());
                }
                doc.append(matchLine);
            }
            roundNumber++;
        }

        doc.append("</table>");
        return doc.toString();
    }

    private String genHtmlResult(List<ResultsTeam> resultTable) {
        String trHeader = "<tr><th></th><th>матчей сыграно</th><th>победы</th><th>ничьи</th><th>поражения</th><th>очки</th></tr>";
        String trTeam = "<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
        StringBuilder doc = new StringBuilder();
        doc.append("<table border=\"1\">").append(trHeader);
        int lastIndex = trTeam.lastIndexOf("</td>");
        int step = 9;
        for (ResultsTeam team : resultTable) {
            StringBuilder buildTrTeam = new StringBuilder();
            buildTrTeam.append(trTeam);
            buildTrTeam.insert(lastIndex, team.getTotal());
            buildTrTeam.insert(lastIndex - step, team.getLosing());
            buildTrTeam.insert(lastIndex - (2 * step), team.getDeadHeat());
            buildTrTeam.insert(lastIndex - (3 * step), team.getWins());
            buildTrTeam.insert(lastIndex - (4 * step), team.getMatchesPlayed());
            buildTrTeam.insert(lastIndex - (5 * step), team.getNameTeam());
            doc.append(buildTrTeam);
        }
        doc.append("</table>");
        return doc.toString();
    }
}
