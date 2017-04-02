package com.quynh.feedmebot;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

import Professor.ProfLogIn;
import Professor.Statistics;
import Student.LogIn;
import Student.Survey;

/**
 * Created by Quynh on 3/21/2017.
 */

/**
 * Shows ListView of all Assignments available within a subject. By pressing on one of the assignments
 * students are directed to the Survey page and profs are directed to the graph status page.
 * This class makes use of the global variable CourseAssignment.assignment to check what courseKey we are looking at.
 * We then find the relevant assignment lists from the db that are connected to this courseKey.
 */

public class AssignmentOverview extends AppCompatActivity {

    public static final String TAG = "AssignmentOverview";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private ListView mListView;
    private TextView description;
    private ArrayAdapter<String> adapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignment_overview);

        description = (TextView) findViewById(R.id.description);
        // Change description of the page according to what user type it is
        if (Objects.equals(ProfLogIn.currentUser.getType(),"teacher")){
            description.setText("Press on an Assignment to see the students' thoughts on it");
        } else if (Objects.equals(LogIn.currentUser.getType(),"student")){
            description.setText("Press on an Assignment to proceed to the Survey page");
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        myRef.child("Subject").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called everytime there is a change to data
                // at this location.
                showAssignments(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // When the user presses on an item in the listview, save the courseKey in a global variable
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String var = parent.getItemAtPosition(position) + ""; // Sets var equal to clicked listview
                // var is now "Assignment 1: Sprint 1"
                String[] temp = var.split(":");     // temp = [Assignment 1, Sprint 1]
                CourseOverview.assignment.setAssignmentName(temp[1]);  // Assignment name is Sprint 1

                String savedAssignmentNr = temp[0].split(" ")[1];  // "Assignment 1" => [Assignment, 1] => 1
                CourseOverview.assignment.setAssignmentNr(savedAssignmentNr);   // Add the chosen assignmentNr to global assignment varriable

                // Check for currentUser type then move to diff pages accordingly
                if (Objects.equals(LogIn.currentUser.getType(), "student")) {
                    Intent survey = new Intent(getApplicationContext(), Survey.class);
                    startActivity(survey);  // Move view to Survey if user is a student
                } else if (Objects.equals(ProfLogIn.currentUser.getType(), "teacher")) {
                    Intent stats = new Intent(getApplicationContext(), Statistics.class);
                    startActivity(stats);  // Move view to show the graphs if user is a teacher
                }
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showAssignments(DataSnapshot dataSnapshot) {
        // subjectMap is the dictionary: generated_keys = courseInfo
        HashMap<String, Object> subjectMap = (HashMap<String, Object>) dataSnapshot.getValue();
        String assignmentString;
        // Make a list of all the assignments available in the subject
        Map<String,Integer>  activeAssignments = new LinkedHashMap<>();    // Make a Map that will receive all info from db

        for (Object courseInfo : subjectMap.values()) {    // For each courseInfo under Subject
            HashMap<String, Object> course_info = (HashMap<String, Object>) courseInfo;  // Cast Object to HashMap
            // If courseKey is the current course, add all assignments to activeAssignments
            if (Objects.equals(course_info.get("courseKey"), CourseOverview.assignment.getCourseKey())){
                Log.d(TAG,"subjectMap: "+subjectMap.values().toString());
                Log.d(TAG,"course_info: "+course_info.toString());

                HashMap<String,Object> assignmentDict = (HashMap<String, Object>) course_info.get("assignments");
                for (Object assignmentNr : assignmentDict.keySet()) {    // Iterate thru the "assignments" table's keys
                    assignmentString = assignmentNr.toString();      // Each keys in the assignment table is "Assignment 1"
                    String reformat = assignmentString + ": " + assignmentDict.get(assignmentNr);  // "Assignment 1: Sprint 1"
                    String[] split = assignmentString.split(" ");     // Split so we have [Assignment, 1]
                    activeAssignments.put(reformat,Integer.parseInt(split[1]));  // Add entry ("Assignment 1", 1) to Map
                }
            }
        }
        // Sort the activeAssignments Map after finish making it
        Map<String,Integer> sorted_activeAssignments = sortMap(activeAssignments);
        ArrayList<String> final_list = new ArrayList<>(sorted_activeAssignments.keySet());  // make a List out of the keys: "Assignment 1"

        // The ListView shows the items from the array activeSubjects using an adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, final_list);
        mListView.setAdapter(adapter);
    }

    // sortMap takes in a Map (Dictionary), sort it after the values (integer) then return the sorted LinkedHashMap
    private Map<String, Integer> sortMap( Map<String, Integer> unsorted ){
        List<Map.Entry<String, Integer>> entries =
                new ArrayList<Map.Entry<String, Integer>>(unsorted.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b){
                return a.getValue().compareTo(b.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
