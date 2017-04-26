package com.quynh.feedmebot;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import NonActivities.Assignment;
import Professor.ProfLogIn;
import Student.LogIn;
import Student.Predictor;

/**
 * Created by Quynh on 3/13/2017.
 */

/**
 * CourseOverview shows a ListView of all courses the user is taking or teaching.
 * When we press on an item, user is directed to a list of all assignments. (AssignmentOverview)
 *
 * The ListView is data taken from the StudentSubject table in the database.
 * We will also make a global variable courseKey (the subject chosen by user) within an Assignment object
 * to be used in AssignmentOverview and Survey.
 */

/** TODO: Make an onBack function in the Views ahead of this view. This is because after choosing an assignment,
 * TODO: the value is saved such that even after moving ahead, assnNr will be saved. We have to erase it after moving away,
 * TODO: or the "Please choose a course before proceeding." error won't take effect when we arrive at this View again.
 */


public class CourseOverview extends AppCompatActivity {
    public static final String TAG = "CourseOverview";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private ListView mListView;
    private ArrayAdapter<String> adapter;

    private Button assignmentButton;
    private Button gradeButton;
    private Button addCourseButton;

    private String clickedCourse;

    public static Assignment assignment;  // This is the global variable that will be accessed in other classes!


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_overview);
        assignment = new Assignment();

        clickedCourse = "";

        mListView = (ListView) findViewById(R.id.listview);

        addCourseButton = (Button) findViewById(R.id.addCourseButton);
        assignmentButton = (Button) findViewById(R.id.assignmentButton);
        gradeButton = (Button) findViewById(R.id.gradeButton);
        gradeButton.setVisibility(View.INVISIBLE);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        if (Objects.equals(LogIn.currentUser.getType(),"student")) {     // Check what type of user is currently logged in
            // Every time there is a change to the database at StudentSubject, this activates
            myRef.child("StudentSubject").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    showDataStudent(dataSnapshot);
                    gradeButton.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if (Objects.equals(LogIn.currentUser.getType(),"teacher")){   // Objects.equals() is null-safe!
            // Every time there is a change to the database at Subject, this activates
            myRef.child("Subject").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    showDataTeacher(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        // When the user presses on an item in the listview, save the courseKey in a global variable
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String var = parent.getItemAtPosition(position) + ""; // Sets var equal to clicked listview
                clickedCourse = var.trim();
                assignment.setCourseKey(clickedCourse);   // Save the courseKey to a global var
                toastMessage("You chose the course: " + clickedCourse);
            }
        });

        assignmentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (! Objects.equals(assignment.getCourseKey(),null )) {  // If user has chosen a course
                    Intent assignment = new Intent(getApplicationContext(), AssignmentOverview.class);
                    startActivity(assignment);  // Move view to AssignmentOverview
                } else {
                    toastMessage("Please choose a course before proceeding.");
                }
            }
        });

        gradeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (! Objects.equals(assignment.getCourseKey(), null)) {  // If user has chosen a course
                    Intent predictor = new Intent(getApplicationContext(), Predictor.class);
                    startActivity(predictor);  // Move view to Student.Predictor
                } else {
                    toastMessage("Please choose a course before proceeding.");
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDataStudent(DataSnapshot dataSnapshot){
        HashMap<String,Object> studMap = (HashMap<String,Object>) dataSnapshot.getValue();

        // Make a list of all the subjects the student is attending
        ArrayList<String> attendingSubjects = new ArrayList<>();

        for (Object courseInfo: studMap.values()){    // Each key is random generated push(), each value = courseInfo
            HashMap<String,Object> course_info = (HashMap<String,Object>) courseInfo;  // Cast Object to HashMap
            // If the email in a StudentSubject entry is the same as the currently logged in Student
            if (Objects.equals(course_info.get("email"),LogIn.currentUser.getEmail())){
                attendingSubjects.add(course_info.get("courseKey").toString());  // Add the course into attendingSubjects
            }
        }

        // The ListView shows the items from the array activeSubjects using an adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, attendingSubjects);
        mListView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)      // For using Objects.equals() in case one of them is null
    private void showDataTeacher(DataSnapshot dataSnapshot) {
        // subjectMap is the dictionary: generated_keys = courseInfo
        HashMap<String,Object> subjectMap = (HashMap<String,Object>) dataSnapshot.getValue();

        // Make a list of all the subjects the teacher is teaching
        ArrayList<String> activeSubjects = new ArrayList<>();
        for (Object courseInfo : subjectMap.values()){    // For each courseInfo under Subject
            HashMap<String,Object> course_info = (HashMap<String,Object>) courseInfo;  // Cast Object to HashMap
            // If currentUser is the owner of the Course
            if (Objects.equals(course_info.get("owner"),LogIn.currentUser.getEmail())){
                activeSubjects.add(course_info.get("courseKey").toString());  // Add the course into activeSubjects
            }
        }

        // The ListView shows the items from the array activeSubjects using an adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, activeSubjects);
        mListView.setAdapter(adapter);
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void goToAddCourse(View view) {
        Intent intent = new Intent(this, AddCourse.class);
        startActivity(intent);
    }
}