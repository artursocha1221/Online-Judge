package com.example.Online.Judge.dtos;

public class ScoreboardOutDto {
    private Long userId;
    private int totalSolved;

    public ScoreboardOutDto() {
    }

    public ScoreboardOutDto(Long userId, int totalSolved) {
        this.userId = userId;
        this.totalSolved = totalSolved;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setTotalSolved(int totalSolved) {
        this.totalSolved = totalSolved;
    }

    public Long getUserId() {
        return userId;
    }
    public int getTotalSolved() {
        return totalSolved;
    }
}
