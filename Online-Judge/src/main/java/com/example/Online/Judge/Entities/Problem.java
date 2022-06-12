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

    public void setId(Long id) {
        this.id = id;
    }
    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Long getId() {
        return id;
    }
    public String getStatement() {
        return statement;
    }
}
