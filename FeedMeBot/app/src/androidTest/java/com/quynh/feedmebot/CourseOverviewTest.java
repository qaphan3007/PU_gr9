package com.quynh.feedmebot;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by qpb96 on 26.04.2017.
 */
public class CourseOverviewTest {


    CourseOverview test;
    @Rule
    public ActivityTestRule<CourseOverview> mActivityRule =
            new ActivityTestRule<>(CourseOverview.class);

    @Test
    public void course() {

        test.onCreate(null);

        onView(withId(R.id.course_overview))
                .perform(click());

    }

}