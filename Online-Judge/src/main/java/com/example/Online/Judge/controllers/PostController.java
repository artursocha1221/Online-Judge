package com.example.Online.Judge.controllers;

import com.example.Online.Judge.dtos.*;
import com.example.Online.Judge.exceptions.*;
import com.example.Online.Judge.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    private final ResponseEntity<String> CREATED = new ResponseEntity<String>(HttpStatus.CREATED);
    private final ResponseEntity<String> BAD_REQUEST = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    private final ResponseEntity<String> FORBIDDEN = new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    private final ResponseEntity<String> NOT_FOUND = new ResponseEntity<String>(HttpStatus.NOT_FOUND);

    private ResponseEntity<String> response;

    @PostMapping("/problem")
    public ResponseEntity<String> addProblem(@RequestBody ProblemInDto problemDto) {
        response = CREATED;
        try {
            postService.addProblem(problemDto.getStatement(), problemDto.getUserId());
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
    public ResponseEntity<String> addTest(@RequestBody TestInDto testInDto) {
        response = CREATED;
        try {
            postService.addTest(testInDto.getInput(), testInDto.getOutput(), testInDto.getProblemId(), testInDto.getUserId());
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
    public ResponseEntity<String> addSolution(@RequestBody SolutionInDto solutionDto) {
        try {
            response = new ResponseEntity<>(postService.addSolution(solutionDto.getCode(), solutionDto.getProblemId(),
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
    public ResponseEntity<String> addUser(@RequestBody UserInDto userDto) {
        response = CREATED;
        try {
            postService.addUser(userDto.getNickname(), userDto.getEmail(), userDto.getRole());
        } catch (IncorrectAttributeException e) {
            response = BAD_REQUEST;
            System.out.println(e.getMessage());
        }
        return response;
    }

    @PostMapping("/friend")
    public ResponseEntity<String> addFriend(@RequestBody FriendInDto friendInDto) {
        response = CREATED;
        try {
            postService.addFriend(friendInDto.getUserId(), friendInDto.getFriendId());
        } catch (NoEntityException e) {
            response = NOT_FOUND;
            System.out.println(e.getMessage());
        } catch (AccessDenied2Exception e) {
            response = FORBIDDEN;
            System.out.println(e.getMessage());
        }
        return response;
    }
}
