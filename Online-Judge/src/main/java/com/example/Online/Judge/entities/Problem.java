package com.example.Online.Judge.entities;

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
    private Long userId;

    public Problem() {
    }

    public Problem(String statement, Long userId) {
        this.statement = statement;
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setStatement(String statement) {
        this.statement = statement;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }
    public String getStatement() {
        return statement;
    }
    public Long getUserId() {
        return userId;
    }
}
