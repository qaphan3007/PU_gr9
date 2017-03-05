package com.quynh.feedmebotv03;

import android.support.annotation.NonNull;

/**
 * Created by Kv1402 on 05.03.2017.
 */

public class Assignment {

    private String key; //assignment key

    private  String courseKey; //the course key;
    private Assignment[] assignmentName;

    //the assigment number
    private String assignmentNr;
    //the assignment hours
    private int assignmentHours;
    //assignment difficulties
    private String assignmentLevel;
    private String[] resources;



    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }





    public Assignment(){

    }

    public Assignment(String courseKey, String assignmentNr){
        this.courseKey = courseKey;
        this.assignmentNr = assignmentNr;
    }



    public Assignment[] getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(Assignment[] assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentNr() {
        return assignmentNr;
    }

    public void setAssignmentNr(String assignmentNr) {
        this.assignmentNr = assignmentNr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCourseKey() {
        return courseKey;
    }

    public int getAssignmentHours() {
        return assignmentHours;
    }

    public void setAssignmentHours(int assignmentHours) {
        this.assignmentHours = assignmentHours;
    }

    public String getAssignmentLevel() {
        return assignmentLevel;
    }

    public void setAssignmentLevel(String assignmentLevel) {
        this.assignmentLevel = assignmentLevel;
    }

    public String[] getResources() {
        return resources;
    }

    public void setResources(String[] resources) {
        this.resources = resources;
    }
}
