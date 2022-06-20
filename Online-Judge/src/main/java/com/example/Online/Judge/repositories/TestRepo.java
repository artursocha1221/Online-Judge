package com.example.Online.Judge.repositories;

import com.example.Online.Judge.entities.Test;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TestRepo extends CrudRepository<Test, Long> {
    @Query("SELECT t FROM Test t WHERE t.problemId = ?1")
    ArrayList<Test> find(Long problemId);
}
