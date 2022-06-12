package com.example.Online.Judge;

import com.example.Online.Judge.DTOs.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private Service service;

    @PostMapping("/problem")
    public void addProblem(@RequestBody String statement) {
        service.addProblem(statement);
    }

    @PostMapping("/test")
    public void addTest(@RequestBody TestDto testDto) {
        service.addTest(testDto.getInput(), testDto.getOutput(), testDto.getProblemId());
    }
}
