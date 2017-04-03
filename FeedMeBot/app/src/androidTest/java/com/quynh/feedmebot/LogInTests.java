package com.quynh.feedmebot;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

// Remember to import libs

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import Student.LogIn;

/**
 * Created by qpb96 on 23.03.2017.
 */


// Must have this type of method to test app when running
@RunWith(AndroidJUnit4.class)
public class LogInTests {

    // test android activity
    @Rule
    public ActivityTestRule<LogIn> mActivityRule =
            new ActivityTestRule<>(LogIn.class);


@Test

public void testLogIn(){

    onView(withId(R.id.textView))
            .check(matches(withText("Student Registration")));
    //Test will enter a name into the EditText control
    //Finds the view with the ID
    // Tapping a text input
    //close keyboard
    onView(withId(R.id.editTextEmail))
            .perform(typeText("qpb@hotmail.com"), closeSoftKeyboard())
            .check(matches(isDisplayed()));


    //Test will eneter password in the password field
    onView((withId(R.id.editTextPassword)))
            .perform(typeText("quitphuong"), closeSoftKeyboard())
            .check(matches(isDisplayed()));




    onView(withText("Login")).perform(click());

}
@Test
public void SignUpTest(){
    onView(withId(R.id.editTextEmail))
            .perform(typeText("testKODE@hotmail.com"), closeSoftKeyboard())
            .check(matches(isDisplayed()));


    //Test will eneter password in the password field
    onView((withId(R.id.editTextPassword)))
            .perform(typeText("1111111111"), closeSoftKeyboard())
            .check(matches(isDisplayed()));

    onView(withText("Sign up")).perform(click());
}
@Test
public void surveyButton(){
    onView(withId(R.id.button10))
            .perform(click())



    ;

}
@Test
public void profLogIn(){

    //LogIn->Prof->LogIn
    onView(withId(R.id.buttonProf)).perform(click());
    onView(withId(R.id.buttonStud)).perform(click());

    onView(withId(R.id.buttonProf)).perform(click());
    onView(withId(R.id.editTextEmail))
            .perform(typeText("proftest123@gmail.com"))
            .check(matches(isDisplayed()));

    onView(withId(R.id.editTextPassword))
            .perform(typeText("1111111111"))
            .check(matches(isDisplayed()));

    onView(withId(R.id.buttonSignIn))
            .perform(click());

}

//public void profSignUp(){
//   onView(withId(R.id.buttonProf)).perform(click());

//    onView(withId(R.id.editTextEmail))
//            .perform(typeText("gruppe9PROFF@gmail.com"))
//            .check(matches(isDisplayed()));

//    onView(withId(R.id.editTextPassword))
//            .perform(typeText("1111111111"))
//            .check(matches(isDisplayed()));

//    onView(withId(R.id.buttonSignup))
//            .perform(click());
//}

}





