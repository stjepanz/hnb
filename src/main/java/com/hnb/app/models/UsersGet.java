package com.hnb.app.models;

public class UsersGet {
    private String username, roles;

    public UsersGet() {
    }

    public UsersGet(String username, String roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
