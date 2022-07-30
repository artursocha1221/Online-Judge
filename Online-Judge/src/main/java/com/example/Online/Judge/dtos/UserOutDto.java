package com.example.Online.Judge.dtos;

public class UserOutDto {
    private String nickname;
    private String email;
    private String role;
    private Boolean isActive;

    public UserOutDto(String  nickname, String  email, String role, Boolean isActive) {
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
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
    public Boolean getActive() {
        return isActive;
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
    public void setActive(Boolean active) {
        isActive = active;
    }
}
