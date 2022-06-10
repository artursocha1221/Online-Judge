package com.example.Online.Judge.Repositories;

import com.example.Online.Judge.Entities.Solution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepo extends CrudRepository<Solution, Long> {
}
