package com.example.Online.Judge.repositories;

import com.example.Online.Judge.entities.Problem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepo extends CrudRepository<Problem, Long> {
}
