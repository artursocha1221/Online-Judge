package com.example.Online.Judge;

import com.example.Online.Judge.dtos.ProblemDto;
import com.example.Online.Judge.entities.Problem;
import com.example.Online.Judge.entities.Solution;
import com.example.Online.Judge.entities.Test;
import com.example.Online.Judge.entities.User;
import com.example.Online.Judge.exceptions.NoProblemException;
import com.example.Online.Judge.exceptions.NoUserException;
import com.example.Online.Judge.exceptions.WrongRoleException;
import com.example.Online.Judge.repositories.ProblemRepo;
import com.example.Online.Judge.repositories.SolutionRepo;
import com.example.Online.Judge.repositories.TestRepo;
import com.example.Online.Judge.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
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

    public void addProblem(String statement, Long userId)
            throws AccessDeniedException, NoUserException {
        if (userRepo.doesIdExist(userId) == null)
            throw new NoUserException("User with id = " + userId + " does not exist.");
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDeniedException("You are not permitted to add new problems.");
        problemRepo.save(new Problem(statement, userId));
    }

    public void addTest(String input, String output, Long problemId, Long userId)
            throws AccessDeniedException, NoUserException, NoProblemException {
        if (userRepo.doesIdExist(userId) == null)
            throw new NoUserException("User with id = " + userId + " does not exist.");
        if (problemRepo.doesIdExist(problemId) == null)
            throw new NoProblemException("Problem with id = " + problemId + " does not exist.");
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDeniedException("You are not permitted to add new tests.");
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

    public void addUser(String nickname, String email, String role) throws WrongRoleException {
        if (!role.equals("admin") && !role.equals("participant"))
            throw new WrongRoleException("Role " + role + " is incorrect.");
        userRepo.save(new User(nickname, email, role, true));
    }
}
