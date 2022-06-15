package com.example.Online.Judge.Repositories;

import com.example.Online.Judge.Entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    @Query("SELECT u.isActive FROM User u WHERE u.id = ?1")
    Boolean isActive(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isActive = ?2 WHERE u.id = ?1")
    void updateIsActive(Long id, Boolean isActive);
}
