package com.example.Online.Judge.repositories;

import com.example.Online.Judge.entities.Friend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepo extends CrudRepository<Friend, Long> {
}
