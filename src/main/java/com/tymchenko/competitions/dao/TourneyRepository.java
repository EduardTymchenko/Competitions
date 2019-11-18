package com.tymchenko.competitions.dao;

import com.tymchenko.competitions.domain.Tourney;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TourneyRepository extends MongoRepository<Tourney,String> {
//    public Tourney findByNameTourneyAnd(String name);
    boolean existsByNameTourney(String name);
    Tourney findByNameTourney(String nameTourney);
}
