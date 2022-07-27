package com.example.Online.Judge.controllers;

import com.example.Online.Judge.dtos.ScoreboardOutDto;
import com.example.Online.Judge.dtos.TestOutDto;
import com.example.Online.Judge.exceptions.AccessDenied2Exception;
import com.example.Online.Judge.exceptions.NoEntityException;
import com.example.Online.Judge.services.GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class GetController {
    @Autowired
    private GetService getService;

    private final HttpStatus OK = HttpStatus.OK;
    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;

    @GetMapping("/scoreboard")
    public ResponseEntity<List<ScoreboardOutDto>> getScoreboardAll() {
        try {
            return new ResponseEntity<>(getService.getScoreboard(null, null), OK);
        } catch (Exception e) { }
        return (new ExceptionHandler<List<ScoreboardOutDto>>("", OK)).handle();
    }

    @GetMapping("/scoreboard/{userId}")
    public ResponseEntity<List<ScoreboardOutDto>> getScoreboardUser(@PathVariable Long userId,
                                                                    @RequestParam(required = false) Boolean friendsOnly) {
        try {
            return new ResponseEntity<>(getService.getScoreboard(userId, friendsOnly), OK);
        } catch (NoEntityException e) {
            return (new ExceptionHandler<List<ScoreboardOutDto>>(e.getMessage(), NOT_FOUND)).handle();
        } catch (AccessDenied2Exception e) {
            return (new ExceptionHandler<List<ScoreboardOutDto>>(e.getMessage(), FORBIDDEN)).handle();
        }
    }

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<String> getProblem(@PathVariable Long problemId) {
        try {
            return new ResponseEntity<>(getService.getProblem(problemId), OK);
        } catch (NoEntityException e) {
            return (new ExceptionHandler<String>(e.getMessage(), NOT_FOUND)).handle();
        }
    }

    @GetMapping("/test/{problemId}/{userId}")
    public ResponseEntity<List<TestOutDto>> getTests(@PathVariable Long problemId,
                                                     @PathVariable Long userId) {
        try {
            return new ResponseEntity<>(getService.getTests(problemId, userId), OK);
        } catch (NoEntityException e) {
            return (new ExceptionHandler<List<TestOutDto>>(e.getMessage(), NOT_FOUND)).handle();
        } catch (AccessDenied2Exception e) {
            return (new ExceptionHandler<List<TestOutDto>>(e.getMessage(), FORBIDDEN)).handle();
        }
    }
}
