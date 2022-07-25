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

    @GetMapping("/scoreboard")
    public ResponseEntity<List<ScoreboardOutDto>> getScoreboardAll() {
        try {
            return new ResponseEntity<>(getService.getScoreboard(null, null), HttpStatus.OK);
        } catch (Exception e) { }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/scoreboard/{userId}")
    public ResponseEntity<List<ScoreboardOutDto>> getScoreboardUser(@PathVariable Long userId,
                                                                    @RequestParam(required = false) Boolean friendsOnly) {
        ResponseEntity<List<ScoreboardOutDto>> response;
        try {
            response = new ResponseEntity<>(getService.getScoreboard(userId, friendsOnly), HttpStatus.OK);
        } catch (NoEntityException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            System.out.println(e.getMessage());
        } catch (AccessDenied2Exception e) {
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            System.out.println(e.getMessage());
        }
        return response;
    }

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<String> getProblem(@PathVariable Long problemId) {
        ResponseEntity<String> response;
        try {
            response = new ResponseEntity<String>(getService.getProblem(problemId), HttpStatus.OK);
        } catch (NoEntityException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            System.out.println(e.getMessage());
        }
        return response;
    }

    @GetMapping("/test/{problemId}/{userId}")
    public ResponseEntity<List<TestOutDto>> getTests(@PathVariable Long problemId,
                                                     @PathVariable Long userId) {
        ResponseEntity<List<TestOutDto>> response;
        try {
            response = new ResponseEntity<>(getService.getTests(problemId, userId), HttpStatus.OK);
        } catch (NoEntityException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            System.out.println(e.getMessage());
        } catch (AccessDenied2Exception e) {
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            System.out.println(e.getMessage());
        }
        return response;
    }
}
