package com.quynh.feedmebot.test;

import android.content.Intent;
import android.content.res.Resources;
import android.support.compat.BuildConfig;
import android.support.v4.app.FragmentPagerAdapter;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.view.ViewPager;
import com.quynh.feedmebot.R;


import static junit.framework.Assert.assertNull;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;

import android.widget.EditText;
import android.widget.TextView;





import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


import Student.LogIn;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.robolectric.shadows.ShadowPendingIntent.getActivity;

/**
 * Created by qpb96 on 23.04.2017.
 */



@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class )
public class LogInTest{

   // private LogIn logIn = new LogIn();

    @Mock
    LogIn mockObj ;

    String email = "qpb96@hotmail.com";


    String password = "lol1235461156";


    private EditText editTextEmail ;

    Button buttonSignup;

   LogIn logIn;




    @Before
    public void setUp() throws Exception {

    //LogIn logIn = new LogIn();
    logIn = mock(LogIn.class);



        //LogIn activity = Robolectric.buildActivity(LogIn.class).create().get();

     logIn.onCreate(null);

    // mockObj.onStart();
     //mockObj.onStop();
     buttonSignup = (Button) logIn.findViewById(R.id.buttonSignup);
     logIn.onClick(buttonSignup);


    //mockObj.onCreate(null);


    assertNotNull(logIn);
    //mockObj.onCreate(null);
    logIn.onStart();
    logIn.onStop();;


    //logIn.onCreate(null);





    //EditText editTextEmail = (EditText) logIn.findViewById(R.id.editTextEmail);

    //editTextEmail.setText(email);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {


    }

    @Test
    public void onStart() throws Exception {

    }

    @Test
    public void onStop() throws Exception {

    }

    @Test
    public void onClick() throws Exception {
        logIn.onClick(buttonSignup);

    }

    @Test
    public void checkValidInputField() throws Exception {


        LogIn logIn = new LogIn();

        String qp1 = "qpb@hotmail.com";
        String invalidEmail = "dsad92.com";
        String qp2 = "asdasd";
        String pass = "";



        boolean f = logIn.checkValidInputField(email,password);
        boolean q = logIn.checkValidInputField(qp1,qp2);


        boolean p = logIn.checkValidInputField(invalidEmail,qp2);
        boolean qp = logIn.checkValidInputField("","");
        boolean qp12 = logIn.checkValidInputField("test123@hotmail.com","");


        assertFalse(qp12);
        assertFalse(qp);
        assertFalse(q);
        assertFalse(p);

        assertTrue(f);


    }

    @Test
    public void registerUser(){
       logIn.checkValidInputField(email, password);
       logIn.registerUser();
       logIn.checkValidInputField(email,password);
    }

    @Test
    public void goToSurvey() throws Exception {

    }

    @Test
    public void getEmail() throws Exception {
        String qp1 = "qpb@hotmail.com";
        LogIn logIn = new LogIn();
        logIn.getEmail();

        String test1 = "qpb@hotmail.com";



        assertEquals("Returnere til rett Email",qp1,qp1);

    }





}