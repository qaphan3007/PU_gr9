package com.quynh.feedmebotv03;

import java.util.ArrayList;

/**
 * Created by qaphan3007 on 05.03.2017.
 */

// This is the User class that will store all info about the current user from the database.

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
