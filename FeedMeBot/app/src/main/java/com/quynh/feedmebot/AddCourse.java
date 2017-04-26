package com.quynh.feedmebot;

import android.content.Intent;
import android.nfc.Tag;
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
import java.util.List;
import java.util.Objects;

import NonActivities.Assignment;
import Student.LogIn;

public class AddCourse extends AppCompatActivity {
    public static final String  TAG = "AddCourse";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    private int counter = 0;

    private ListView mListView;
    private ArrayAdapter<String> adapter;

    private Button addCourseButton;
    private String clickedCourse;
    private String userID;

    private boolean inCourse;

    private  Assignment assignment;
    private DataSnapshot current_datas_snapshot;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

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
                Log.d(TAG, "showing courses"); //test, remember to remove
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
                String var = parent.getItemAtPosition(position) +"";
                clickedCourse = var.trim();
                assignment.setCourseKey(clickedCourse);
                Log.d(TAG, "Clicked on a course: "+ clickedCourse);
                toastMessage(clickedCourse + " selected!");

            }
        });
        myRef.child("StudentSubject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Checking if student already enrolled in subject.
                //inCourse = alreadyInCourse(dataSnapshot);

                current_datas_snapshot = dataSnapshot;
                boolean in_course = alreadyInCourse(dataSnapshot);
                Log.d(TAG, counter + ", is the Number of times entered alreadyInCourse");

                //startActivity(new Intent(getApplicationContext(), CourseOverview.class));
                if (!in_course){
                    setInCourse(false);
                } else{
                    setInCourse(true);
                }

                Log.d(TAG, "in_Course: " +inCourse );

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(LogIn.currentUser.getType(),"student")){
                    //Check if no subject selected
                    if(!assignment.getCourseKey().isEmpty()){
                   //if(!clickedCourse.isEmpty()){
                    Log.d(TAG,"Incourse at button listener= "+ inCourse);
                        //if(!inCourse){
                        if(!alreadyInCourse(current_datas_snapshot)){
                            createNewStudentSubject();

                        }
                        startActivity(new Intent(getApplicationContext(), CourseOverview.class));

                    }
                }else if(Objects.equals(LogIn.currentUser.getType(),"professor")){
                    //Make professor new owner of class- remeber to check if already owner of class.

                }

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showCourses(DataSnapshot dataSnapshot){
        HashMap<String,Object> subjectMap = (HashMap<String,Object>) dataSnapshot.getValue();

        // Make a list of all the subjects the student is attending
        //ArrayList<String> attendingSubjects = new ArrayList<>();
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

//key: "email,coursekey"
    private void createNewStudentSubject(){
        userRef = mFirebaseDatabase.getReference().child("UserInfo");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        String key = userID+" "+assignment.getCourseKey();
        myRef.child("StudentSubject").child(key);

        myRef.child("StudentSubject").child(key).child("courseKey").setValue(assignment.getCourseKey());
        myRef.child("StudentSubject").child(key).child("email").setValue(LogIn.currentUser.getEmail());

        //location.child("courseKey").setValue(assignment.getCourseKey());
        //location.child("email").setValue(LogIn.currentUser.getEmail());
        //location.child("assignmentTime");
        //location.child("grade");

    }

    private void setInCourse(boolean value){
        inCourse = value;
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private  boolean alreadyInCourse(DataSnapshot dataSnapshot){
        Log.d(TAG, "ClickedCourse: "+clickedCourse);
        if(clickedCourse.equals("")){
            return true;
        }

        HashMap<String, Object> studCourses = (HashMap<String,Object>) dataSnapshot.getValue();
        for (Object courseInfo: studCourses.values()){    // Each key is random generated push(), each value = courseInfo
            Log.d(TAG, courseInfo +" = value for studCourses");
            HashMap<String,Object> course_info = (HashMap<String,Object>) courseInfo;  // Cast Object to HashMap

            // If the email in a StudentSubject entry is the same as the currently logged in Student
          //  Log.d(TAG,"email: " + course_info.get("email") + ",  courseKey: " +course_info.get("courseKey"));

            //Not working, why?? Ask about objects.equals..
            //if ((Objects.equals(course_info.get("email"),LogIn.currentUser.getEmail())) && (Objects.equals(course_info.get("courseKey"), clickedCourse))){
                if((course_info.containsValue(LogIn.currentUser.getEmail()) && course_info.containsValue(assignment.getCourseKey()))){
                    return true;

                // attendingSubjects.add(course_info.get("courseKey").toString());  // Add the course into attendingSubjects
            }

        }
        return false;


    }

    //Not used, safe to delete
    private void goToCourseOverview(View veiw){
        Intent courseOverview = new Intent (this, CourseOverview.class);
        startActivity(courseOverview);
    }


}
