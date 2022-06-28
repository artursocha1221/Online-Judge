package com.example.Online.Judge.dtos;

public class ScoreboardDto {
    private Long userId;
    private int totalSolved;

    public ScoreboardDto() {
    }

    public ScoreboardDto(Long userId, int totalSolved) {
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
