package com.example.Online.Judge.dtos;

public class ScoreboardOutDto {
    private int rank;
    private Long userId;
    private int totalSolved;

    public ScoreboardOutDto() {
    }

    public ScoreboardOutDto(int rank, Long userId, int totalSolved) {
        this.rank = rank;
        this.userId = userId;
        this.totalSolved = totalSolved;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setTotalSolved(int totalSolved) {
        this.totalSolved = totalSolved;
    }

    public int getRank() {
        return rank;
    }
    public Long getUserId() {
        return userId;
    }
    public int getTotalSolved() {
        return totalSolved;
    }
}
