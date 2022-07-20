package com.example.Online.Judge.dtos;

public class SolutionInDto {
    private String code;
    private Long problemId;
    private Long userId;
    private String language;

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
}
