package com.example.Online.Judge;

import com.example.Online.Judge.dtos.ScoreboardDto;
import com.example.Online.Judge.entities.Problem;
import com.example.Online.Judge.entities.Solution;
import com.example.Online.Judge.entities.Test;
import com.example.Online.Judge.entities.User;
import com.example.Online.Judge.exceptions.AccessDenied2Exception;
import com.example.Online.Judge.exceptions.IncorrectAttributeException;
import com.example.Online.Judge.exceptions.NoEntityException;
import com.example.Online.Judge.repositories.ProblemRepo;
import com.example.Online.Judge.repositories.SolutionRepo;
import com.example.Online.Judge.repositories.TestRepo;
import com.example.Online.Judge.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            throws AccessDenied2Exception, NoEntityException {
        if (userRepo.doesIdExist(userId) == null)
            throw new NoEntityException("User", userId);
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDenied2Exception("problem");

        problemRepo.save(new Problem(statement, userId));
    }

    public void addTest(String input, String output, Long problemId, Long userId)
            throws AccessDenied2Exception, NoEntityException {
        if (userRepo.doesIdExist(userId) == null)
            throw new NoEntityException("User", userId);
        if (problemRepo.doesIdExist(problemId) == null)
            throw new NoEntityException("Problem", problemId);
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDenied2Exception("test");

        testRepo.save(new Test(input, output, problemId, userId));
        ArrayList<Solution> solutions = solutionRepo.find(problemId);
        for (Solution solution : solutions) {
            if (!userRepo.isActive(solution.getUserId()))
                continue;
            String newResult = (new StringBuilder(solution.getResults()))
                    .append(TestRunner.result(solution.getCode(), input, output, solution.getLanguage()))
                    .toString();
            solutionRepo.update(solution.getId(), newResult);
        }
    }

    public void addSolution(String code, Long problemId, Long userId, String language)
            throws NoEntityException, IncorrectAttributeException, AccessDenied2Exception {
        if (userRepo.doesIdExist(userId) == null)
            throw new NoEntityException("User", userId);
        if (problemRepo.doesIdExist(problemId) == null)
            throw new NoEntityException("Problem", problemId);
        if (!language.equals("cpp") && !language.equals("java"))
            throw new IncorrectAttributeException("Language", language);
        if (!isExpectedRole(userId, "participant") || !userRepo.isActive(userId))
            throw new AccessDenied2Exception("solution");

        ArrayList<Long> cheaterId = solutionRepo.findCheater(code, problemId, userId, language);
        if (cheaterId.size() != 0) {
            userRepo.updateIsActive(cheaterId.get(0), false);
            userRepo.updateIsActive(userId, false);
            throw new AccessDenied2Exception("solution");
        }
        ArrayList<Test> tests = testRepo.find(problemId);
        StringBuilder result = new StringBuilder("");
        for (Test test : tests)
            result.append(TestRunner.result(code, test.getInput(), test.getOutput(), language));
        solutionRepo.save(new Solution(code, problemId, userId, language, result.toString()));
    }

    public void addUser(String nickname, String email, String role)
            throws IncorrectAttributeException {
        if (!role.equals("admin") && !role.equals("participant"))
            throw new IncorrectAttributeException("Role", role);

        userRepo.save(new User(nickname, email, role, true));
    }

    public List<ScoreboardDto> getScoreboard() {
        List<Long> problemsId = problemRepo.findAllId();
        HashMap<Long, Integer> scoreboard = new HashMap<Long, Integer>();
        for (Long problemId : problemsId) {
            long totalTests = testRepo.findNumberOfTestsById(problemId);
            StringBuilder resultPattern = new StringBuilder("");
            for (int j = 0; j < totalTests; ++j)
                resultPattern.append("Y");
            List<Long> participantsWhoSolved = solutionRepo.findIdsWhoSolved(problemId, resultPattern.toString());
            for (Long participantWhoSolved : participantsWhoSolved) {
                int newScore;
                if (scoreboard.containsKey(participantWhoSolved))
                    newScore = scoreboard.get(participantWhoSolved) + 1;
                else
                    newScore = 1;
                scoreboard.put(participantWhoSolved, newScore);
            }
        }
        List<Long> usersId = userRepo.findAllParticipantsId();
        List<ScoreboardDto> scoreboardDto = new ArrayList<ScoreboardDto>();
        for (Long userId : usersId) {
            int newScore;
            if (scoreboard.containsKey(userId))
                newScore =  scoreboard.get(userId);
            else
                newScore = 0;
            scoreboardDto.add(new ScoreboardDto(userId, newScore));
        }
        scoreboardDto.sort(new ScoreboardComparator());
        return scoreboardDto;
    }
}
