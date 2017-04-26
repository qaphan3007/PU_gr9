package com.quynh.feedmebot.Professor;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.quynh.feedmebot.Profile;
import com.quynh.feedmebot.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import Student.LogIn;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by qpb96 on 03.04.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ProfProfileTests {



    @Rule
    public ActivityTestRule<Profile> mActivityRule =
            new ActivityTestRule<>(Profile.class);

    @Test
    public void editButton() {

        onView(ViewMatchers.withId(R.id.edit_profile))
                .perform(click());


    }
    @Test
    public void courseButton() {
        onView(withId(R.id.course_overview))
                .perform(click());

    }
@Test
    public void signOutButton() {
        onView(withId(R.id.log_out))
                .perform(click());


    }

}
