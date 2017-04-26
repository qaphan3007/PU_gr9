package com.quynh.feedmebot.Professor;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.quynh.feedmebot.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import Professor.ProfLogIn;
import Student.LogIn;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

/**
 * Created by qpb96 on 30.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ProfLoginTests {


    @Rule
    public ActivityTestRule<LogIn> mActivityRule =
            new ActivityTestRule<>(LogIn.class);

    @Test
    public void profLogIn() {

        //LogIn->Prof->LogIn
        onView(ViewMatchers.withId(R.id.buttonProf)).perform(click());
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

       // onView(withId(R.id.edit_profile))
         //       .perform(click());



    }
@Test
    public void logOut(){
        onView(withId(R.id.log_out));

    }

    @Test
    public void coursOverview(){
        onView(withId(R.id.course_overview));
    }


}