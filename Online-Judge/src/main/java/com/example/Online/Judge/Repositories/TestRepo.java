package com.example.Online.Judge.Repositories;

import com.example.Online.Judge.Entities.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends CrudRepository<Test, Long> {
}
