package com.example.Online.Judge.services;

import com.example.Online.Judge.ScoreboardComparator;
import com.example.Online.Judge.dtos.*;
import com.example.Online.Judge.entities.Solution;
import com.example.Online.Judge.exceptions.AccessDenied2Exception;
import com.example.Online.Judge.exceptions.NoEntityException;
import com.example.Online.Judge.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetService {
    @Autowired
    private ProblemRepo problemRepo;
    @Autowired
    private TestRepo testRepo;
    @Autowired
    private SolutionRepo solutionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FriendRepo friendRepo;

    private boolean isExpectedRole(Long userId, String role) {
        return userRepo.findRoleById(userId).equals(role);
    }

    public List<ScoreboardOutDto> getScoreboard(Long userId, Boolean friendsOnly)
            throws NoEntityException, AccessDenied2Exception {
        if (userId != null) {
            if (userRepo.findIdById(userId) == null)
                throw new NoEntityException("User", userId);
            if (!isExpectedRole(userId, "participant"))
                throw new AccessDenied2Exception();
        }

        List<Long> problemsId = problemRepo.findAllIds();
        Map<Long, Integer> scoreboard = new HashMap<>();
        for (Long problemId : problemsId) {
            long totalTests = testRepo.findNumberOfTestsByProblemId(problemId);
            StringBuilder resultPattern = new StringBuilder("");
            for (int j = 0; j < totalTests; ++j)
                resultPattern.append("Y");
            List<Long> participantsWhoSolved = solutionRepo.findIdsWhoSolvedByProblemId(problemId, resultPattern.toString());
            for (Long participantWhoSolved : participantsWhoSolved) {
                int newScore = 1;
                if (scoreboard.containsKey(participantWhoSolved))
                    newScore = scoreboard.get(participantWhoSolved) + 1;
                scoreboard.put(participantWhoSolved, newScore);
            }
        }
        List<Long> usersId = userRepo.findIdsForAllParticipants();
        List<ScoreboardOutDto> scoreboardDto = new ArrayList<>();
        for (Long userIdTemp : usersId) {
            if (!userRepo.isActiveById(userIdTemp))
                continue;
            int newScore = 0;
            if (scoreboard.containsKey(userIdTemp))
                newScore =  scoreboard.get(userIdTemp);
            scoreboardDto.add(new ScoreboardOutDto(-1, userIdTemp, newScore));
        }
        scoreboardDto.sort(new ScoreboardComparator());
        for (int i = 0; i < scoreboardDto.size(); ++i)
            scoreboardDto.get(i).setRank(i + 1);
        if (friendsOnly != null && friendsOnly == true) {
            scoreboardDto = scoreboardDto.stream().
                    filter(s -> {
                        return friendRepo.findIdByUserIdAndFriendId(userId, s.getUserId()) != null;
                    })
                    .toList();
        }
        return scoreboardDto;
    }

    public List<ProblemOutDto> getProblems() {
        return problemRepo.findAllStatements().stream()
                .map(p -> {
                    return new ProblemOutDto(p);
                })
                .toList();
    }

    public List<TestOutDto> getTests(Long problemId, Long userId)
        throws AccessDenied2Exception, NoEntityException {
        if (problemRepo.findIdById(problemId) == null)
            throw new NoEntityException("Problem", problemId);
        if (userRepo.findIdById(userId) == null)
            throw new NoEntityException("User", userId);
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDenied2Exception();

        return testRepo.findTestsByProblemId(problemId).stream()
                .map(t -> {
                    return new TestOutDto(t.getInput(), t.getOutput());
                })
                .toList();
    }

    public List<UserOutDto> getUsers(Long userId)
        throws NoEntityException, AccessDenied2Exception {
        if (userRepo.findIdById(userId) == null)
            throw new NoEntityException("User", userId);
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDenied2Exception();

        return userRepo.findAll().stream()
                .map(u -> {
                    return new UserOutDto(u.getNickname(), u.getEmail(), u.getRole(), u.getIsActive());
                })
                .toList();
    }

    public List<SolutionOutDto> getSolutions(Long userId)
        throws NoEntityException {
        if (userRepo.findIdById(userId) == null)
            throw new NoEntityException("User", userId);

        ArrayList<Solution> solutions;
        if (isExpectedRole(userId, "admin"))
            solutions = solutionRepo.findAllSolutions();
        else
            solutions = solutionRepo.findSolutionsByUserId(userId);
        return solutions.stream()
                .map(s -> {
                    return new SolutionOutDto(s.getCode(), s.getProblemId(), s.getUserId(), s.getLanguage(), s.getResults());
                })
                .toList();
    }
}
