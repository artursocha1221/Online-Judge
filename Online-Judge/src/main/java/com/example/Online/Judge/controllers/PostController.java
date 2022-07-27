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

    private final HttpStatus CREATED = HttpStatus.CREATED;
    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;
    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    @PostMapping("/problem")
    public ResponseEntity<String> addProblem(@RequestBody ProblemInDto problemDto) {
        try {
            postService.addProblem(problemDto.getStatement(), problemDto.getUserId());
            return new ResponseEntity<>(CREATED);
        } catch (NoEntityException e) {
            return (new ExceptionHandler<String>(e.getMessage(), NOT_FOUND)).handle();
        } catch (AccessDenied2Exception e) {
            return (new ExceptionHandler<String>(e.getMessage(), FORBIDDEN)).handle();
        }
    }

    @PostMapping("/test")
    public ResponseEntity<String> addTest(@RequestBody TestInDto testInDto) {
        try {
            postService.addTest(testInDto.getInput(), testInDto.getOutput(), testInDto.getProblemId(), testInDto.getUserId());
            return new ResponseEntity<>(CREATED);
        } catch (NoEntityException e) {
            return (new ExceptionHandler<String>(e.getMessage(), NOT_FOUND)).handle();
        } catch (AccessDenied2Exception e) {
            return (new ExceptionHandler<String>(e.getMessage(), FORBIDDEN)).handle();
        }
    }

    @PostMapping("/solution")
    public ResponseEntity<String> addSolution(@RequestBody SolutionInDto solutionDto) {
        try {
            return new ResponseEntity<>(postService.addSolution(solutionDto.getCode(), solutionDto.getProblemId(),
                    solutionDto.getUserId(), solutionDto.getLanguage()), CREATED);
        } catch (NoEntityException e) {
            return (new ExceptionHandler<String>(e.getMessage(), NOT_FOUND)).handle();
        } catch (IncorrectAttributeException e) {
            return (new ExceptionHandler<String>(e.getMessage(), BAD_REQUEST)).handle();
        } catch (AccessDenied2Exception e) {
            return (new ExceptionHandler<String>(e.getMessage(), FORBIDDEN)).handle();
        }
    }

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody UserInDto userDto) {
        try {
            postService.addUser(userDto.getNickname(), userDto.getEmail(), userDto.getRole());
            return new ResponseEntity<>(CREATED);
        } catch (IncorrectAttributeException e) {
            return (new ExceptionHandler<String>(e.getMessage(), BAD_REQUEST)).handle();
        }
    }

    @PostMapping("/friend")
    public ResponseEntity<String> addFriend(@RequestBody FriendInDto friendInDto) {
        try {
            postService.addFriend(friendInDto.getUserId(), friendInDto.getFriendId());
            return new ResponseEntity<>(CREATED);
        } catch (NoEntityException e) {
            return (new ExceptionHandler<String>(e.getMessage(), NOT_FOUND)).handle();
        } catch (AccessDenied2Exception e) {
            return (new ExceptionHandler<String>(e.getMessage(), FORBIDDEN)).handle();
        }
    }
}
