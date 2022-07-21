package com.example.Online.Judge.services;

import com.example.Online.Judge.TestRunner;
import com.example.Online.Judge.entities.*;
import com.example.Online.Judge.exceptions.*;
import com.example.Online.Judge.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PostService {
    @Autowired
    private ProblemRepo problemRepo;
    @Autowired
    private TestRepo testRepo;
    @Autowired
    private SolutionRepo solutionRepo;
    @Autowired
    private UserRepo userRepo;

    private final Set<String> languages = new HashSet<>();
    private final Set<String> roles = new HashSet<>();

    PostService() {
        languages.add("java");
        languages.add("cpp");
        roles.add("admin");
        roles.add("participant");
    }

    private boolean isExpectedRole(Long id, String role) {
        return userRepo.findRoleById(id).equals(role);
    }

    public void addProblem(String statement, Long userId)
            throws AccessDenied2Exception, NoEntityException {
        if (userRepo.findIdById(userId) == null)
            throw new NoEntityException("User", userId);
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDenied2Exception("problem");

        problemRepo.save(new Problem(statement, userId));
    }

    public void addTest(String input, String output, Long problemId, Long userId)
            throws AccessDenied2Exception, NoEntityException {
        if (userRepo.findIdById(userId) == null)
            throw new NoEntityException("User", userId);
        if (problemRepo.findIdById(problemId) == null)
            throw new NoEntityException("Problem", problemId);
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDenied2Exception("test");

        testRepo.save(new Test(input, output, problemId, userId));
        List<Solution> solutions = solutionRepo.findSolutionsByProblemId(problemId);
        for (Solution solution : solutions) {
            if (!userRepo.isActiveById(solution.getUserId()))
                continue;
            String newResult = (new StringBuilder(solution.getResults()))
                    .append(TestRunner.result(solution.getCode(), input, output, solution.getLanguage()))
                    .toString();
            solutionRepo.updateResultsById(solution.getId(), newResult);
        }
    }

    public String addSolution(String code, Long problemId, Long userId, String language)
            throws NoEntityException, IncorrectAttributeException, AccessDenied2Exception {
        if (userRepo.findIdById(userId) == null)
            throw new NoEntityException("User", userId);
        if (problemRepo.findIdById(problemId) == null)
            throw new NoEntityException("Problem", problemId);
        if (!language.contains(language))
            throw new IncorrectAttributeException("Language", language);
        if (!isExpectedRole(userId, "participant") || !userRepo.isActiveById(userId))
            throw new AccessDenied2Exception("solution");

        Long cheaterId = solutionRepo.findCheater(code, problemId, userId, language);
        if (cheaterId != null) {
            userRepo.updateIsActiveById(cheaterId, false);
            userRepo.updateIsActiveById(userId, false);
            throw new AccessDenied2Exception("solution");
        }
        List<Test> tests = testRepo.findTestsByProblemId(problemId);
        StringBuilder result = new StringBuilder("");
        for (Test test : tests)
            result.append(TestRunner.result(code, test.getInput(), test.getOutput(), language));
        solutionRepo.save(new Solution(code, problemId, userId, language, result.toString()));
        return result.toString();
    }

    public void addUser(String nickname, String email, String role)
            throws IncorrectAttributeException {
        if (!roles.contains(role))
            throw new IncorrectAttributeException("Role", role);

        userRepo.save(new User(nickname, email, role, true));
    }
}
