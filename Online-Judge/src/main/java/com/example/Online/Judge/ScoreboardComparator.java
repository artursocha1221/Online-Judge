package com.example.Online.Judge;

import com.example.Online.Judge.dtos.ScoreboardDto;

import java.util.Comparator;

public class ScoreboardComparator implements Comparator<ScoreboardDto> {
    @Override
    public int compare(ScoreboardDto a, ScoreboardDto b) {
        if (a.getTotalSolved() > b.getTotalSolved())
            return -1;
        if (a.getTotalSolved() < b.getTotalSolved())
            return 1;
        if (a.getUserId() < b.getUserId())
            return -1;
        return 1;
    }
}
