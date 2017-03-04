package com.quynh.feedmebotv03;


import java.util.ArrayList;

public class User {

    private String email, type;
    private ArrayList<String> types;

    public User() {
        types = new ArrayList<String>();
        types.add("teacher");
        types.add("student");
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public void setType(String type) {
        if (types.contains(type)){
            this.type = type;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
