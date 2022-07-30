package com.example.Online.Judge.repositories;

import com.example.Online.Judge.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    @Query("SELECT u.isActive FROM User u WHERE u.id = ?1")
    Boolean isActiveById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isActive = ?2 WHERE u.id = ?1")
    void updateIsActiveById(Long id, Boolean isActive);

    @Query("SELECT u.role FROM User u WHERE u.id = ?1")
    String findRoleById(Long id);

    @Query("SELECT u.id FROM User u WHERE u.id = ?1")
    Long findIdById(Long id);

    @Query("SELECT u.id FROM User u WHERE u.role = 'participant'")
    ArrayList<Long> findIdsForAllParticipants();

    @Query("SELECT u.id FROM User u WHERE u.email = ?1")
    Long findIdByEmail(String email);

    @Query("SELECT u FROM User u")
    ArrayList<User> findAll();
}
