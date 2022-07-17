package com.example.Online.Judge;

import com.example.Online.Judge.dtos.*;
import com.example.Online.Judge.exceptions.AccessDenied2Exception;
import com.example.Online.Judge.exceptions.IncorrectAttributeException;
import com.example.Online.Judge.exceptions.NoEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private Service service;

    private final ResponseEntity<String> OK = new ResponseEntity<String>(HttpStatus.OK);
    private final ResponseEntity<String> CREATED = new ResponseEntity<String>(HttpStatus.CREATED);
    private final ResponseEntity<String> BAD_REQUEST = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    private final ResponseEntity<String> FORBIDDEN = new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    private final ResponseEntity<String> NOT_FOUND = new ResponseEntity<String>(HttpStatus.NOT_FOUND);

    private ResponseEntity<String> response;

    @PostMapping("/problem")
    public ResponseEntity<String> addProblem(@RequestBody ProblemDto problemDto) {
        response = CREATED;
        try {
            service.addProblem(problemDto.getStatement(), problemDto.getUserId());
        } catch (NoEntityException e) {
            response = NOT_FOUND;
            System.out.println(e.getMessage());
        } catch (AccessDenied2Exception e) {
            response = FORBIDDEN;
            System.out.println(e.getMessage());
        }
        return response;
    }

    @PostMapping("/test")
    public ResponseEntity<String> addTest(@RequestBody TestDto testDto) {
        response = CREATED;
        try {
            service.addTest(testDto.getInput(), testDto.getOutput(), testDto.getProblemId(), testDto.getUserId());
        } catch (NoEntityException e) {
            response = NOT_FOUND;
            System.out.println(e.getMessage());
        } catch (AccessDenied2Exception e) {
            response = FORBIDDEN;
            System.out.println(e.getMessage());
        }
        return response;
    }

    @PostMapping("/solution")
    public ResponseEntity<String> addSolution(@RequestBody SolutionDto solutionDto) {
        try {
            response = new ResponseEntity<>(service.addSolution(solutionDto.getCode(), solutionDto.getProblemId(),
                    solutionDto.getUserId(), solutionDto.getLanguage()), HttpStatus.CREATED);
        } catch (NoEntityException e) {
            response = NOT_FOUND;
            System.out.println(e.getMessage());
        } catch (IncorrectAttributeException e) {
            response = BAD_REQUEST;
            System.out.println(e.getMessage());
        } catch (AccessDenied2Exception e) {
            response = FORBIDDEN;
            System.out.println(e.getMessage());
        }
        return response;
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        response = CREATED;
        try {
            service.addUser(userDto.getNickname(), userDto.getEmail(), userDto.getRole());
        } catch (IncorrectAttributeException e) {
            response = BAD_REQUEST;
            System.out.println(e.getMessage());
        }
        return response;
    }

    @GetMapping("/scoreboard")
    public ResponseEntity<List<ScoreboardDto>> getScoreboard() {
        return new ResponseEntity<>(service.getScoreboard(), HttpStatus.OK);
    }

    @GetMapping("/problem/{id}")
    public ResponseEntity<String> getProblem(@PathVariable Long id) {
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        try {
            response = new ResponseEntity<String>(service.getProblem(id), HttpStatus.OK);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }
}
