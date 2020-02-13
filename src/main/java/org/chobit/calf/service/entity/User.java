package org.chobit.calf.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author robin
 */
public class User extends AbstractEntity {

    private String username;
    @JsonIgnore
    private String password;

    private String email;

    private String nickname;

    public User() {
    }

    public User(String username, String password, String email, String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
