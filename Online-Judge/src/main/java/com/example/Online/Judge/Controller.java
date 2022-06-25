package com.example.Online.Judge;

import com.example.Online.Judge.dtos.ProblemDto;
import com.example.Online.Judge.dtos.SolutionDto;
import com.example.Online.Judge.dtos.TestDto;
import com.example.Online.Judge.dtos.UserDto;
import com.example.Online.Judge.exceptions.AccessDenied2Exception;
import com.example.Online.Judge.exceptions.IncorrectAttributeException;
import com.example.Online.Judge.exceptions.NoEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private Service service;

    @PostMapping("/problem")
    public void addProblem(@RequestBody ProblemDto problemDto) {
        try {
            service.addProblem(problemDto.getStatement(), problemDto.getUserId());
        } catch (NoEntityException | AccessDenied2Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/test")
    public void addTest(@RequestBody TestDto testDto) {
        try {
            service.addTest(testDto.getInput(), testDto.getOutput(), testDto.getProblemId(), testDto.getUserId());
        } catch (NoEntityException | AccessDenied2Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/solution")
    public void addSolution(@RequestBody SolutionDto solutionDto) {
        try {
            service.addSolution(solutionDto.getCode(), solutionDto.getProblemId(), solutionDto.getUserId(), solutionDto.getLanguage());
        } catch (NoEntityException | IncorrectAttributeException | AccessDenied2Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserDto userDto) {
        try {
            service.addUser(userDto.getNickname(), userDto.getEmail(), userDto.getRole());
        } catch (IncorrectAttributeException e) {
            System.out.println(e.getMessage());
        }
    }
}
