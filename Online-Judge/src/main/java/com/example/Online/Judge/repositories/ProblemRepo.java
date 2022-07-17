package com.example.Online.Judge.repositories;

import com.example.Online.Judge.entities.Problem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProblemRepo extends CrudRepository<Problem, Long> {
    @Query("SELECT p.id FROM Problem p WHERE p.id = ?1")
    Long findIdById(Long id);

    @Query("SELECT p.id FROM Problem p")
    ArrayList<Long> findAllIds();

    @Query("SELECT p.statement FROM Problem p WHERE p.id = ?1")
    String findStatementById(Long id);
}
