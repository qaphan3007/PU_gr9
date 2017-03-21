package com.quynh.feedmebot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Quynh on 3/13/2017.
 */

/**
 * CourseOverview shows a ListView of all courses the user is taking or teaching.
 * When we press on an item and hit button Go, depending on what type the user is, they will be
 * redirected to either AssignmentOverview (shows all avail assignments in that subject) or ProfGraph.
 *
 * The ListView is data taken from the StudentSubject table in the database.
 * We will also make a global variable courseKey (the subject chosen by user) to be used in
 * AssignmentOverview and Survey.
 */

public class CourseOverview extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_overview);

    }
}