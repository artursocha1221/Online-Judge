package com.example.Online.Judge.Repositories;

import com.example.Online.Judge.Entities.Solution;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface SolutionRepo extends CrudRepository<Solution, Long> {
    @Query("SELECT s FROM Solution s WHERE s.problemId = ?1")
    ArrayList<Solution> find(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Solution s SET s.results = ?2 WHERE s.id = ?1")
    void update(Long id, String results);
}
