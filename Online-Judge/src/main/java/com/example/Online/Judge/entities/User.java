package com.example.Online.Judge.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String role;
    private Boolean isActive;

    public User() {
    }

    public User(String nickname, String email, String role, Boolean isActive) {
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }
    public String getNickname() {
        return nickname;
    }
    public String getEmail() {
        return email;
    }
    public String getRole() {
        return role;
    }
    public Boolean getIsActive() {
        return isActive;
    }
}
