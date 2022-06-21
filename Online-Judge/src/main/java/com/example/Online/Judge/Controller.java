package com.example.Online.Judge;

import com.example.Online.Judge.dtos.ProblemDto;
import com.example.Online.Judge.dtos.SolutionDto;
import com.example.Online.Judge.dtos.TestDto;
import com.example.Online.Judge.dtos.UserDto;
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
        service.addProblem(problemDto.getStatement(), problemDto.getUserId());
    }

    @PostMapping("/test")
    public void addTest(@RequestBody TestDto testDto) {
        service.addTest(testDto.getInput(), testDto.getOutput(), testDto.getProblemId(), testDto.getUserId());
    }

    @PostMapping("/solution")
    public void addSolution(@RequestBody SolutionDto solutionDto) {
        service.addSolution(solutionDto.getCode(), solutionDto.getProblemId(), solutionDto.getUserId(), solutionDto.getLanguage());
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserDto userDto) {
        service.addUser(userDto.getNickname(), userDto.getEmail(), userDto.getRole());
    }
}
