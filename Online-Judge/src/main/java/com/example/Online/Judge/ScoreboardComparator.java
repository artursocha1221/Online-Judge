package com.example.Online.Judge;

import com.example.Online.Judge.dtos.ScoreboardOutDto;

import java.util.Comparator;

public class ScoreboardComparator implements Comparator<ScoreboardOutDto> {
    @Override
    public int compare(ScoreboardOutDto a, ScoreboardOutDto b) {
        if (a.getTotalSolved() > b.getTotalSolved())
            return -1;
        if (a.getTotalSolved() < b.getTotalSolved())
            return 1;
        if (a.getUserId() < b.getUserId())
            return -1;
        return 1;
    }
}
