package com.example.Online.Judge.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String input, output;
    private Long problemId;

    public Test() {
    }

    public Test(String input, String output, Long problemId) {
        this.input = input;
        this.output = output;
        this.problemId = problemId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setInput(String input) {
        this.input = input;
    }
    public void setOutput(String output) {
        this.output = output;
    }
    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getId() {
        return id;
    }
    public String getInput() {
        return input;
    }
    public String getOutput() {
        return output;
    }
    public Long getProblemId() {
        return problemId;
    }
}
