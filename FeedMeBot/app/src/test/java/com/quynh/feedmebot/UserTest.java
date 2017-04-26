package com.quynh.feedmebot;

import org.junit.Before;
import org.junit.Test;

import NonActivities.User;

import static org.junit.Assert.*;

/**
 * Created by qpb96 on 26.04.2017.
 */
public class UserTest {

    String email, type, name, date;
    String phone;
    User user;


    @Before
    public void setUp() throws Exception {

        user = new User();

    }
    @Test
    public void getType() throws Exception {

    }

    @Test
    public void getEmail() throws Exception {

    }

    @Test
    public void getName() throws Exception {

    }

    @Test
    public void getPhone() throws Exception {

    }

    @Test
    public void getDate() throws Exception {

    }

    @Test
    public void setType() throws Exception {
        User qp = new User();
        qp.setType("student");

        String test = "";

        String major = "student";
        assertEquals("Student skal være lik getType()",major,qp.getType());
        assertEquals("",test, user.getType());

    }

    @Test
    public void setEmail() throws Exception {

        User qp = new User();
        String testMail = "qpb96@hotmail.com";

        qp.setEmail("qpb96@hotmail.com");

        assertEquals("qpb96@hotmail.com skal være lik med getEmail()",testMail, qp.getEmail());


    }

    @Test
    public void setName() throws Exception {

        User name = new User();
        String testName = "Njål";

        name.setName("Njål");

        assertEquals("Njål skal være like med getName()",testName, name.getName());
    }

    @Test
    public void setPhone() throws Exception {

        User mob = new User();
        String mobTest ="47611944";

        mob.setPhone("47611944");

        assertEquals("Mob lik mob", mobTest, mob.getPhone());
    }

    @Test
    public void setDate() throws Exception {

        User date = new User();

        String dateTest = "110896";
        date.setDate("110896");
        assertEquals("Dato skal være like med setDate()",dateTest, date.getDate()) ;



    }

}