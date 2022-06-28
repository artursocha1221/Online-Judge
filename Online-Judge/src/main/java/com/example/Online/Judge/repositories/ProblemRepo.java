package com.example.Online.Judge.repositories;

import com.example.Online.Judge.entities.Problem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepo extends CrudRepository<Problem, Long> {
    @Query("SELECT p.id FROM Problem p WHERE p.id = ?1")
    Long doesIdExist(Long id);

    @Query("SELECT p.id FROM Problem p")
    List<Long> findAllId();
}
