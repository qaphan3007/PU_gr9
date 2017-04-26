package com.quynh.feedmebot.Professor;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.quynh.feedmebot.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import Professor.Statistics;
import Student.LogIn;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by qpb96 on 26.04.2017.
 */

@RunWith(AndroidJUnit4.class)
public class StatisticsTest {

    @Rule
    public ActivityTestRule<Statistics> mActivityRule =
            new ActivityTestRule<>(Statistics.class);

    @Test
    public void graph(){
        onView(ViewMatchers.withId(R.id.assignmentTime))
                .check(matches(isDisplayed()));
    }

}