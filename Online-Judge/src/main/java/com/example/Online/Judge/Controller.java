package com.example.Online.Judge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private Service service;

    @PostMapping("/addProblem")
    public void addProblem(@RequestBody String statement) {
        service.addProblem(statement);
    }
}
