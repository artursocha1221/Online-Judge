package com.example.Online.Judge.repositories;

import com.example.Online.Judge.entities.Solution;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface SolutionRepo extends CrudRepository<Solution, Long> {
    @Query("SELECT s FROM Solution s WHERE s.problemId = ?1")
    ArrayList<Solution> find(Long problemId);

    @Transactional
    @Modifying
    @Query("UPDATE Solution s SET s.results = ?2 WHERE s.id = ?1")
    void update(Long id, String results);

    @Query("SELECT s.userId FROM Solution s WHERE s.code = ?1 AND s.problemId = ?2 AND s.userId != ?3 AND s.language = ?4")
    ArrayList<Long> findCheater(String code, Long problemId, Long userId, String language);
}