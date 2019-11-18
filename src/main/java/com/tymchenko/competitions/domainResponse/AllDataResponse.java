package com.tymchenko.competitions.domainResponse;

import com.tymchenko.competitions.domain.ResultsTeam;
import com.tymchenko.competitions.domain.Round;
import lombok.Data;

import java.util.List;
@Data
public class AllDataResponse {
    private List<ResultsTeam> teamResultResposeList;
    private List<Round> timetable;
}
