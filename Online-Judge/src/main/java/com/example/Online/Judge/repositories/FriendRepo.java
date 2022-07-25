package com.example.Online.Judge.repositories;

import com.example.Online.Judge.entities.Friend;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepo extends CrudRepository<Friend, Long> {
    @Query("SELECT f.id FROM Friend f WHERE f.userId = ?1 AND f.friendId = ?2")
    Long findIdByUserIdAndFriendId(Long userId, Long FriendId);
}
