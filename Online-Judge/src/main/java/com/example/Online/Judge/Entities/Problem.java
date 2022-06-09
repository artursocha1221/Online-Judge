package com.example.Online.Judge.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String statement;

    public Problem() {
    }

    public Problem(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return this.statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
