package com.quynh.feedmebot;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import Student.LogIn;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by qpb96 on 23.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class EditProfileTests {
    // test android activity
    @Rule
    public ActivityTestRule<EditProfile> mActivityRule =
            new ActivityTestRule<>(EditProfile.class);

    @Test
    public void testLogInWithAnExistingAccount() {
        onView(withId(R.id.nameEdit))
            .perform(typeText("Quit Phuong"), closeSoftKeyboard());

        onView(withId(R.id.majorEdit))
                .perform(typeText("MTDT"), closeSoftKeyboard());

        onView(withId(R.id.birthdayEdit))
                .perform(typeText("110896"), closeSoftKeyboard());

        onView(withId(R.id.phoneEdit))
                .perform(typeText("72607428"), closeSoftKeyboard());

        onView(withText("Edit"))
                .perform(click());

        onView(withId(R.id.edit_profile))
                .perform(click());

        onView(withId(R.id.edit))
                .perform(click());
    }








}