package com.example.Online.Judge.services;

import com.example.Online.Judge.TestRunner;
import com.example.Online.Judge.entities.*;
import com.example.Online.Judge.exceptions.*;
import com.example.Online.Judge.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {
    private final ProblemRepo problemRepo;
    private final TestRepo testRepo;
    private final SolutionRepo solutionRepo;
    private final UserRepo userRepo;
    private final FriendRepo friendRepo;

    private final Set<String> languages = new HashSet<>();
    private final Set<String> roles = new HashSet<>();

    @Autowired
    public PostService(ProblemRepo problemRepo,
                      TestRepo testRepo,
                      SolutionRepo solutionRepo,
                      UserRepo userRepo,
                      FriendRepo friendRepo) {
        languages.add("java");
        languages.add("cpp");
        roles.add("admin");
        roles.add("participant");

        this.problemRepo = problemRepo;
        this.testRepo = testRepo;
        this.solutionRepo = solutionRepo;
        this.userRepo = userRepo;
        this.friendRepo = friendRepo;
    }

    private boolean isExpectedRole(Long id, String role) {
        return userRepo.findRoleById(id).equals(role);
    }

    public void addUser(String nickname, String email, String role)
            throws IncorrectAttributeException {
        if (!roles.contains(role))
            throw new IncorrectAttributeException("Role", role);

        userRepo.save(new User(nickname, email, role, true));
        Long newUserId = userRepo.findIdByEmail(email);
        if (role.equals("participant"))
            friendRepo.save(new Friend(newUserId, newUserId));
    }

    public void addProblem(String statement, Long userId)
            throws AccessDenied2Exception, NoEntityException {
        userRepo.findIdById(userId)
                .orElseThrow(() -> new NoEntityException("User", userId));
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDenied2Exception();

        problemRepo.save(new Problem(statement, userId));
    }

    public void addTest(String input, String output, Long problemId, Long userId)
            throws AccessDenied2Exception, NoEntityException {
        userRepo.findIdById(userId)
                .orElseThrow(() -> new NoEntityException("User", userId));
        problemRepo.findIdById(problemId)
                   .orElseThrow(() -> new NoEntityException("Problem", problemId));
        if (!isExpectedRole(userId, "admin"))
            throw new AccessDenied2Exception();

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
        userRepo.findIdById(userId)
                .orElseThrow(() -> new NoEntityException("User", userId));
        problemRepo.findIdById(problemId)
                .orElseThrow(() -> new NoEntityException("Problem", problemId));
        if (!language.contains(language))
            throw new IncorrectAttributeException("Language", language);
        if (!isExpectedRole(userId, "participant") || !userRepo.isActiveById(userId))
            throw new AccessDenied2Exception();

        Optional<Long> cheaterId = solutionRepo.findCheater(code, problemId, userId, language);
        if (cheaterId.isPresent()) {
            userRepo.updateIsActiveById(cheaterId.get(), false);
            userRepo.updateIsActiveById(userId, false);
            throw new AccessDenied2Exception();
        }
        List<Test> tests = testRepo.findTestsByProblemId(problemId);
        StringBuilder result = new StringBuilder("");
        for (Test test : tests)
            result.append(TestRunner.result(code, test.getInput(), test.getOutput(), language));
        solutionRepo.save(new Solution(code, problemId, userId, language, result.toString()));
        return result.toString();
    }

    public void addFriend(Long userId, Long friendId)
            throws NoEntityException, AccessDenied2Exception {
        userRepo.findIdById(userId)
                .orElseThrow(() -> new NoEntityException("User", userId));
        userRepo.findIdById(friendId)
                .orElseThrow(() -> new NoEntityException("User", friendId));
        if (!isExpectedRole(userId, "participant") || !isExpectedRole(friendId, "participant"))
            throw new AccessDenied2Exception();

        friendRepo.save(new Friend(userId, friendId));
    }
}
