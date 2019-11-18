package com.tymchenko.competitions.service;

import com.tymchenko.competitions.dao.TourneyRepository;
import com.tymchenko.competitions.domain.Match;
import com.tymchenko.competitions.domain.Round;
import com.tymchenko.competitions.domain.Tourney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TourneySeviceImpl implements TourneyService {

    @Autowired
    TourneyRepository repository;

    @Override
    @Transactional
    public List<Round> getTimeTableByNameTourneyAndAmount(String nameTourney, int amountTeams) {
        if (repository.existsByNameTourney(nameTourney)){
            String errMess = "Соревнование с названием \"" + nameTourney + "\" уже существует";
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, errMess);
        }
        Tourney newTourney = new Tourney(nameTourney, amountTeams);

        for (Tourney item : repository.findAll()) {
            System.out.println(item.toString());
        }
        return repository.save(newTourney).getTimetable();
    }

    @Override
    @Transactional
    public Tourney getTourneyByName(String nameTourney) {
        Tourney tourney = repository.findByNameTourney(nameTourney);
        if (tourney == null){
            String errMess = "Соревнование с названием \"" + nameTourney + "\" в базе не существует!!!";
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, errMess);
        }
        return tourney;
    }

    @Override
    @Transactional
    public Tourney changeScore(String nameTourney, String nameTeam1, int goals1, String nameTeam2, int goals2) {
        Tourney tourney = getTourneyByName(nameTourney);
        boolean isTeam1 = false;
        boolean isTeam2 = false;
        for (Round round: tourney.getTimetable()) {
            for (Match match : round.getTeamsListInRound()) {
                if (match.getTeamFirst().getNameTeam().equals(nameTeam1)) isTeam1 = true;
                if (match.getTeamSecond().getNameTeam().equals(nameTeam2)) isTeam2 = true;
                if (match.getTeamFirst().getNameTeam().equals(nameTeam1) && match.getTeamSecond().getNameTeam().equals(nameTeam2)){
                    match.setGoalsScoredFirst(goals1);
                    match.setGoalsScoredSecond(goals2);
                    return repository.save(tourney);
                }
            }

        }
        if (isTeam1 && isTeam2) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ошибка в рассписании");
        else if (isTeam1) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ошибка в названии 2-й команды");
        else if (isTeam2) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ошибка в названии 1-й команды");
        else throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ошибка в названии 1-й и 2-й команды");
    }
}
