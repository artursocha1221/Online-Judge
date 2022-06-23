package com.example.Online.Judge;

import com.example.Online.Judge.dtos.ProblemDto;
import com.example.Online.Judge.dtos.SolutionDto;
import com.example.Online.Judge.dtos.TestDto;
import com.example.Online.Judge.dtos.UserDto;
import com.example.Online.Judge.exceptions.NoProblemException;
import com.example.Online.Judge.exceptions.NoUserException;
import com.example.Online.Judge.exceptions.WrongRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;
import java.nio.file.AccessDeniedException;

@RestController
public class Controller {
    @Autowired
    private Service service;

    @PostMapping("/problem")
    public void addProblem(@RequestBody ProblemDto problemDto) {
        try {
            service.addProblem(problemDto.getStatement(), problemDto.getUserId());
        } catch (NoUserException | AccessDeniedException e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/test")
    public void addTest(@RequestBody TestDto testDto) {
        try {
            service.addTest(testDto.getInput(), testDto.getOutput(), testDto.getProblemId(), testDto.getUserId());
        } catch (NoUserException | NoProblemException | AccessDeniedException e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/solution")
    public void addSolution(@RequestBody SolutionDto solutionDto) {
        service.addSolution(solutionDto.getCode(), solutionDto.getProblemId(), solutionDto.getUserId(), solutionDto.getLanguage());
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserDto userDto) {
        try {
            service.addUser(userDto.getNickname(), userDto.getEmail(), userDto.getRole());
        } catch (WrongRoleException e) {
            System.out.println(e.getMessage());
        }
    }
}
