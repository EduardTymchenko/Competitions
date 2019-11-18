package com.tymchenko.competitions;

import com.tymchenko.competitions.dao.TourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CompetitionsApplication implements ApplicationRunner {

    @Autowired
    TourneyRepository repository;

    public static void main   (String[] args) {
        SpringApplication.run(CompetitionsApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments arg0) {
//        repository.deleteAll();
//        repository.save(new Tourney("First", 4));
//        repository.save(new Tourney("Too", 6));
//        for (Tourney item: repository.findAll()) {
//            System.out.println(item.toString());
//        }

    }

}
