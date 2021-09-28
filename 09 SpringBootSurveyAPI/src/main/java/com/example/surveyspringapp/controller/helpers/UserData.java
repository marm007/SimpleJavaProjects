package com.example.surveyspringapp.controller.helpers;

public class UserData {

    private String username;
    private int size;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "[\"username:\"" + username + ",\"size:\"" + size + "]";
    }
}
