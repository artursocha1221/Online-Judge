package com.example.Online.Judge.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Long problemId;
    private String results;

    public Solution() {
    }

    public Solution(String code, Long problemId, String results) {
        this.code = code;
        this.problemId = problemId;
        this.results = results;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getCode() {
        return this.code;
    }

    public Long getProblemId() {
        return this.problemId;
    }

    public String getResults() {
        return this.results;
    }
}
