package com.quynh.feedmebotv03;

import com.quynh.feedmebotv03.Assignment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kv1402 on 05.03.2017.
 */

public class Course {

    public static final String NAME = "name";
    public static final String OWNERS = "owners";

    private String key;
    private String courseID;
    private String courseName;
    private String courseOwner;
    private Assignment[] assignments;
    private Map<String, Boolean> owners;


    public Course(){

    }

    public Course(String name, String uid){
        this.courseName = name;
        this.owners = new HashMap<>();
        owners.put(uid,true);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseOwner() {
        return courseOwner;
    }

    public void setCourseOwner(String courseOwner) {
        this.courseOwner = courseOwner;
    }

    public Map<String, Boolean> getOwners() {
        return owners;
    }

    public void setOwners(Map<String, Boolean> owners) {
        this.owners = owners;
    }
}
