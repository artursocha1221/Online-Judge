package com.example.Online.Judge;

import com.example.Online.Judge.Entities.Problem;
import com.example.Online.Judge.Entities.Solution;
import com.example.Online.Judge.Entities.Test;
import com.example.Online.Judge.Entities.User;
import com.example.Online.Judge.Repositories.ProblemRepo;
import com.example.Online.Judge.Repositories.SolutionRepo;
import com.example.Online.Judge.Repositories.TestRepo;
import com.example.Online.Judge.Repositories.UserRepo;
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
    private UserRepo userRepo;

    public void addProblem(String statement) {
        problemRepo.save(new Problem(statement));
    }

    public void addTest(String input, String output, Long problemId) {
        testRepo.save(new Test(input, output, problemId));
        ArrayList<Solution> solutions = solutionRepo.find(problemId);
        for (int i = 0; i < solutions.size(); ++i) {
            Solution solution = solutions.get(i);
            if (!userRepo.isActive(solution.getUserId()))
                continue;
            String newResult = (new StringBuilder(solution.getResults()))
                    .append(TestRunner.getResult(solution.getCode(), input, output))
                    .toString();
            solutionRepo.update(solution.getId(), newResult);
        }
    }

    public boolean addSolution(String code, Long problemId, Long userId) {
        if (!userRepo.isActive(userId))
            return false;
        ArrayList<Long> cheaterId = solutionRepo.findCheater(code, problemId, userId);
        if (cheaterId.size() != 0) {
            userRepo.updateIsActive(cheaterId.get(0), false);
            userRepo.updateIsActive(userId, false);
            return false;
        }
        ArrayList<Test> tests = testRepo.find(problemId);
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < tests.size(); ++i) {
            Test test = tests.get(i);
            result.append(TestRunner.getResult(code, test.getInput(), test.getOutput()));
        }
        solutionRepo.save(new Solution(code, problemId, userId, result.toString()));
        return true;
    }

    public void addUser(String nickname, String email) {
        userRepo.save(new User(nickname, email, true));
    }
}
