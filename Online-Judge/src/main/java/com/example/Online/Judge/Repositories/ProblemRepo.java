package com.example.Online.Judge.Repositories;

import com.example.Online.Judge.Entities.Problem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepo extends CrudRepository<Problem, Long> {
}