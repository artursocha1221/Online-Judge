package com.example.Online.Judge;

import com.example.Online.Judge.Entities.Problem;
import com.example.Online.Judge.Repositories.ProblemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {
    @Autowired
    private ProblemRepo problemRepo;

    public void addProblem(String statement) {
        problemRepo.save(new Problem(statement));
    }
}
