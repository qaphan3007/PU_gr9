package com.quynh.feedmebot;

import org.junit.Before;
import org.junit.Test;

import NonActivities.Assignment;

import static org.junit.Assert.*;

/**
 * Created by qpb96 on 26.04.2017.
 */
public class AssignmentTest {

    Assignment ass;

    @Before
    public void setUp() throws Exception {

        ass = new Assignment("TMA4100", "12");
    }

    @Test
    public void setCourseKey() throws Exception {
        Assignment test12 = new Assignment();
        test12.setCourseKey("MAT123");
    }

    @Test
    public void getAssignmentName() throws Exception {


    }

    @Test
    public void setAssignmentName() throws Exception {
        Assignment test0 = new Assignment();
        test0.setAssignmentName("qPB");
        test0.getAssignmentName();
    }

    @Test
    public void getAssignmentNr() throws Exception {
        ass.getAssignmentNr();

    }

    @Test
    public void setAssignmentNr() throws Exception {
        Assignment test1 = new Assignment();
        test1.setAssignmentNr("1234");
    }

    @Test
    public void getKey() throws Exception {
        Assignment test = new Assignment();
        test.setKey("TDT");
        test.getKey();
    }

    @Test
    public void setKey() throws Exception {
    Assignment test = new Assignment();
        test.setKey("TDT");

    }

    @Test
    public void getCourseKey() throws Exception {

        String cours = "TMA4100";

        assertEquals("courseKey", cours, ass.getCourseKey());

    }

    @Test
    public void getAssignmentHours() throws Exception {
        Assignment test = new Assignment();
        test.setAssignmentHours(12);
        test.getAssignmentHours();

    }

    @Test
    public void setAssignmentHours() throws Exception {
        Assignment test = new Assignment();
        test.setAssignmentHours(12);

    }

    @Test
    public void getAssignmentLevel() throws Exception {
        Assignment test = new Assignment();
        test.setAssignmentLevel("Hard");
        test.getAssignmentLevel();

    }

    @Test
    public void setAssignmentLevel() throws Exception {

    }

    @Test
    public void getResources() throws Exception {
        Assignment test = new Assignment();
        test.setResources(null);
        test.getResources();
    }

    @Test
    public void setResources() throws Exception {

    }

}