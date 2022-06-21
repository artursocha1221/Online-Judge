package com.example.Online.Judge;

import com.example.Online.Judge.dtos.ProblemDto;
import com.example.Online.Judge.entities.Problem;
import com.example.Online.Judge.entities.Solution;
import com.example.Online.Judge.entities.Test;
import com.example.Online.Judge.entities.User;
import com.example.Online.Judge.repositories.ProblemRepo;
import com.example.Online.Judge.repositories.SolutionRepo;
import com.example.Online.Judge.repositories.TestRepo;
import com.example.Online.Judge.repositories.UserRepo;
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

    boolean isExpectedRole(Long id, String role) {
        return userRepo.role(id).equals(role);
    }

    public boolean addProblem(String statement, Long userId) {
        if (isExpectedRole(userId, "admin")) {
            problemRepo.save(new Problem(statement, userId));
            return true;
        }
        return false;
    }

    public boolean addTest(String input, String output, Long problemId, Long userId) {
        if (!isExpectedRole(userId, "admin"))
            return false;
        testRepo.save(new Test(input, output, problemId, userId));
        ArrayList<Solution> solutions = solutionRepo.find(problemId);
        for (int i = 0; i < solutions.size(); ++i) {
            Solution solution = solutions.get(i);
            if (!userRepo.isActive(solution.getUserId()))
                continue;
            String newResult = (new StringBuilder(solution.getResults()))
                    .append(TestRunner.result(solution.getCode(), input, output, solution.getLanguage()))
                    .toString();
            solutionRepo.update(solution.getId(), newResult);
        }
        return true;
    }

    public boolean addSolution(String code, Long problemId, Long userId, String language) {
        if (!isExpectedRole(userId, "participant") || !userRepo.isActive(userId))
            return false;
        ArrayList<Long> cheaterId = solutionRepo.findCheater(code, problemId, userId, language);
        if (cheaterId.size() != 0) {
            userRepo.updateIsActive(cheaterId.get(0), false);
            userRepo.updateIsActive(userId, false);
            return false;
        }
        ArrayList<Test> tests = testRepo.find(problemId);
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < tests.size(); ++i) {
            Test test = tests.get(i);
            result.append(TestRunner.result(code, test.getInput(), test.getOutput(), language));
        }
        solutionRepo.save(new Solution(code, problemId, userId, language, result.toString()));
        return true;
    }

    public boolean addUser(String nickname, String email, String role) {
        userRepo.save(new User(nickname, email, role, true));
        return true;
    }
}
