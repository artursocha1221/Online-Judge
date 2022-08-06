package com.example.Online.Judge.dtos;

public class SolutionOutDto {
    private String code;
    private Long problemId;
    private Long userId;
    private String language;
    private String results;

    public SolutionOutDto() {
    }

    public SolutionOutDto(String code, Long problemId, Long userId, String language, String results) {
        this.code = code;
        this.problemId = problemId;
        this.userId = userId;
        this.language = language;
        this.results = results;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setResults(String results) {
        this.results = results;
    }

    public String getCode() {
        return code;
    }
    public Long getProblemId() {
        return problemId;
    }
    public Long getUserId() {
        return userId;
    }
    public String getLanguage() {
        return language;
    }
    public String getResults() {
        return results;
    }
}
