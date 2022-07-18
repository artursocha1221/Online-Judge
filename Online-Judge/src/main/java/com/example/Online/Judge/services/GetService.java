package com.example.Online.Judge.services;

import com.example.Online.Judge.ScoreboardComparator;
import com.example.Online.Judge.dtos.ScoreboardDto;
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

    public List<ScoreboardDto> getScoreboard() {
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
        List<ScoreboardDto> scoreboardDto = new ArrayList<>();
        for (Long userId : usersId) {
            if (!userRepo.isActiveById(userId))
                continue;;
            int newScore = 0;
            if (scoreboard.containsKey(userId))
                newScore =  scoreboard.get(userId);
            scoreboardDto.add(new ScoreboardDto(userId, newScore));
        }
        scoreboardDto.sort(new ScoreboardComparator());
        return scoreboardDto;
    }

    public String getProblem(Long problemId) throws NoEntityException {
        String statememnt = problemRepo.findStatementById(problemId);
        if (statememnt == null)
            throw new NoEntityException("Problem", problemId);
        return statememnt;
    }
}
