package com.example.Online.Judge.controllers;

import com.example.Online.Judge.dtos.ScoreboardDto;
import com.example.Online.Judge.exceptions.NoEntityException;
import com.example.Online.Judge.services.GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetController {
    @Autowired
    private GetService getService;

    @GetMapping("/scoreboard")
    public ResponseEntity<List<ScoreboardDto>> getScoreboard() {
        return new ResponseEntity<>(getService.getScoreboard(), HttpStatus.OK);
    }

    @GetMapping("/problem/{id}")
    public ResponseEntity<String> getProblem(@PathVariable Long id) {
        ResponseEntity<String> response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        try {
            response = new ResponseEntity<String>(getService.getProblem(id), HttpStatus.OK);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }
}
