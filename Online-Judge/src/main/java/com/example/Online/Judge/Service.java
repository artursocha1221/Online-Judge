package com.example.Online.Judge;

import com.example.Online.Judge.Entities.Problem;
import com.example.Online.Judge.Entities.Solution;
import com.example.Online.Judge.Entities.Test;
import com.example.Online.Judge.Repositories.ProblemRepo;
import com.example.Online.Judge.Repositories.SolutionRepo;
import com.example.Online.Judge.Repositories.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Service {
    @Autowired
    private ProblemRepo problemRepo;
    @Autowired
    private TestRepo testRepo;
    @Autowired
    private SolutionRepo solutionRepo;
    @Autowired
    private TestRunner testRunner;

    public void addProblem(String statement) {
        problemRepo.save(new Problem(statement));
    }

    public void addTest(String input, String output, Long problemId) {
        testRepo.save(new Test(input, output, problemId));
        ArrayList<Solution> solutions = solutionRepo.find(problemId);
        for (int i = 0; i < solutions.size(); ++i)
        {
            Solution solution = solutions.get(i);
            String newResult = (new StringBuilder(solution.getResults()))
                    .append(testRunner.getResult(solution.getCode(), input, output))
                    .toString();
            solutionRepo.update(solution.getId(), newResult);
        }
    }
}
