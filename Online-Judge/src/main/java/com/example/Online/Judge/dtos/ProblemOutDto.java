package com.example.Online.Judge.dtos;

public class ProblemOutDto {
    private String statement;

    public ProblemOutDto(String statement) {
        this.statement = statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }
}
