package com.quynh.feedmebot;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import Student.LogIn;

public class AddCourse extends AppCompatActivity {
    public static final String  TAG = "AddCourse";

    //Firebase stuff
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private DataSnapshot current_datas_snapshot;

    private int counter = 0;

    private ListView mListView;
    private ArrayAdapter<String> adapter;

    private Button addCourseButton;
    private String clickedCourse;
    private String userID;

    private boolean inCourse;

    private  Assignment assignment;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //Initializing
        mListView = (ListView) findViewById(R.id.listview);
        assignment = new Assignment();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        clickedCourse = "";
        addCourseButton = (Button) findViewById(R.id.addCourseButton);
        current_datas_snapshot = null;
        //assignment = CourseOverview.assignment;


        //Showing avaliable courses
        myRef.child("Subject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showCourses(dataSnapshot);
                Log.d(TAG, "showing courses"); //Functinal test
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // When the user presses on an item in the listview, save the courseKey as a String
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Creates temporary string for display
                String var = parent.getItemAtPosition(position) +"";
                clickedCourse = var.trim();
                assignment.setCourseKey(clickedCourse);
                Log.d(TAG, "Clicked on a course: "+ clickedCourse);
                toastMessage(clickedCourse + " selected!");

            }
        });
        //Listener on StudentSubject in DB
        myRef.child("StudentSubject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                current_datas_snapshot = dataSnapshot;
                boolean in_course = alreadyInCourse(dataSnapshot);
                Log.d(TAG, counter + ", is the Number of times entered alreadyInCourse");

                //Help to make addCourseButton work properly
                if (!in_course){
                    setInCourse(false);
                } else{
                    setInCourse(true);
                }

                Log.d(TAG, "in_Course: " +inCourse ); //Functinal test

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });
        //Supposed to hide addCourseButton if professor.
        if(Objects.equals(LogIn.currentUser.getType(),"professor")){
            addCourseButton.setVisibility(View.GONE);
        }
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if user is student
                if(Objects.equals(LogIn.currentUser.getType(),"student")){
                    //Check if no subject selected
                    if(!assignment.getCourseKey().isEmpty()){

                    Log.d(TAG,"Incourse at button listener= "+ inCourse); //Functional test

                        //Checks if student already enrolled in class
                        if(!alreadyInCourse(current_datas_snapshot)){
                            createNewStudentSubject();

                        }
                        //Goes back to CourseOverview
                        startActivity(new Intent(getApplicationContext(), CourseOverview.class));

                    }
                }else if(Objects.equals(LogIn.currentUser.getType(),"professor")){
                   toastMessage("You do not have permission to add course!");

                }

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showCourses(DataSnapshot dataSnapshot){
        HashMap<String,Object> subjectMap = (HashMap<String,Object>) dataSnapshot.getValue();
        ArrayList<String> allSubjects = new ArrayList<>();

        for (Object courseInfo: subjectMap.values()){    // Each key is random generated push(), each value = courseInfo
            HashMap<String,Object> course_info = (HashMap<String,Object>) courseInfo;  // Cast Object to HashMap
            // If the email in a StudentSubject entry is the same as the currently logged in Student
                allSubjects.add(course_info.get("courseKey").toString());  // Add the course into attendingSubjects

        }

        // The ListView shows the items from the array activeSubjects using an adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, allSubjects);
        mListView.setAdapter(adapter);
    }

    //Creates new StudentSubject in DB
    private void createNewStudentSubject(){
        userRef = mFirebaseDatabase.getReference().child("UserInfo");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid(); //Gets unique user id
        String key = userID+" "+assignment.getCourseKey(); //Sets the key to userID + courseKey
        //Adding to DB
        myRef.child("StudentSubject").child(key);
        myRef.child("StudentSubject").child(key).child("courseKey").setValue(assignment.getCourseKey());
        myRef.child("StudentSubject").child(key).child("email").setValue(LogIn.currentUser.getEmail());
    }
    // Help method to add studentSubject
    private void setInCourse(boolean value){
        inCourse = value;
    }
    //Method for toasting
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    //Method checking if student already enrolled in subject
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private  boolean alreadyInCourse(DataSnapshot dataSnapshot){
        Log.d(TAG, "ClickedCourse: "+clickedCourse);
        if(clickedCourse.equals("")){
            return true;
        }

        HashMap<String, Object> studCourses = (HashMap<String,Object>) dataSnapshot.getValue();
        //Looping through StudentSubject
        for (Object courseInfo: studCourses.values()){    // Each key is random generated push(), each value = courseInfo
            Log.d(TAG, courseInfo +" = value for studCourses"); //Functional test
            HashMap<String,Object> course_info = (HashMap<String,Object>) courseInfo;  // Cast Object to HashMap

                //Returns true if current user is enrolled in subject
                if((course_info.containsValue(LogIn.currentUser.getEmail()) && course_info.containsValue(assignment.getCourseKey()))){
                    return true;
                }
        }
        //User not enrolled
        return false;

    }
}
