package Student;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quynh.feedmebot.AssignmentOverview;
import com.quynh.feedmebot.CourseOverview;
import com.quynh.feedmebot.Profile;
import com.quynh.feedmebot.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import NonActivities.Assignment;
import NonActivities.User;

// Survey class is a common class with questions for all assignments in all subjects
public class Survey extends AppCompatActivity {
    final String TAG = "Survey";


    //TODO: Må hente ut hvilket fag vi jobber med, hvilken øving og email til bruker.
    // en survey består av : assignmentNr, CourseKey, difficulty, feedback, rescources, og studmail.
    // mangler CourseKey, studmail. Kanskje lage getters i respektive klasser?

    // to get email : LogIn.currentUser.getEmail();


    private DataSnapshot current_datas_snapshot;

    // Initialize needed items
    private TextView textViewTest;
    private TextView textView;
    private TextView textViewHour;
    private SeekBar seekBarHour;
    private SeekBar seekBarDifficultLevel;
    private Button sendButton;
    private CheckBox checkBox_youtube, checkBox_piazza, checkBox_lectures, checkBox_syllabus, checkBox_other,checkBox_stackOverflow;
    private Assignment assignment = new Assignment();



    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey) ;

        //Assignment assignment = new Assignment();
        checkBox_lectures = (CheckBox) (findViewById(R.id.checkBox_lecture));
        checkBox_youtube = (CheckBox) findViewById(R.id.checkBox_youtube);
        checkBox_piazza = (CheckBox) findViewById(R.id.checkBox_piazza);
        //checkBox_syllabus = (CheckBox) findViewById(R.id.checkBox_syllabus);
        checkBox_other = (CheckBox) findViewById(R.id.checkBox_other);
        checkBox_stackOverflow = (CheckBox) findViewById(R.id.checkBox_stack);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();





        textViewHour = (TextView) (findViewById(R.id.textViewHour));
        seekBarHour = (SeekBar) findViewById(R.id.seekBarHours);
        seekBarHour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    textViewHour.setText("Jeg kokte øvingen......"); // prints how many hours.
                    assignment.setAssignmentHours(progress); // Sets the the hours spent to the assignment object

                } else {
                    textViewHour.setText(progress + " hours"); // prints how many hours.
                    assignment.setAssignmentHours(progress); // Sets the the hours spent to the assignment object
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        textView= (TextView) findViewById(R.id.textViewA2);
        seekBarDifficultLevel = (SeekBar) findViewById(R.id.seekBarDifficultLevel);
        seekBarDifficultLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress==0) {
                    textView.setText("Very easy"); // prints level in textView
                    assignment.setAssignmentLevel("Very easy"); // Sets the difficultylevel to the assignment object
                } else if  (progress==1) {
                    textView.setText("Easy");
                    assignment.setAssignmentLevel("Easy"); // Sets the difficultylevel to the assignment object
                }else if  (progress==2) {
                    textView.setText("Medium");
                    assignment.setAssignmentLevel("Medium"); // Sets the difficultylevel to the assignment object
                }else if  (progress==3) {
                    textView.setText("OK");
                    assignment.setAssignmentLevel("OK"); // Sets the difficultylevel to the assignment object
                }else if  (progress==4) {
                    textView.setText("Challenging");
                    assignment.setAssignmentLevel("Challenging"); // Sets the difficultylevel to the assignment object
                }else if  (progress==5) {
                    textView.setText("Very hard");
                    assignment.setAssignmentLevel("I give up"); // Sets the difficultylevel to the assignment object
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mDatabase.child("StudentSubject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                current_datas_snapshot = dataSnapshot;
                //upDateAssignmentTime(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                completeSurvey(); //Gets resourcesfrom checkboxes
                updateData(); //Sends assignmentObject to DB.
                // On click sends all the data from assignment to the database
                upDateAssignmentTime();
                //updateData();
                // Then sends the user back to the previous page
                Toast.makeText(Survey.this,"Sucsessfully answered" + CourseOverview.assignment.getAssignmentName(), Toast.LENGTH_SHORT).show();
                goToProfile(view);
            }
        });


    }

    private void goToProfile(View view){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    //Sends data to DB
    private void updateData(){
        HashMap<String, String> assignmentMap = new HashMap<String, String>();

        Log.d(TAG, "Assignment number: "+ CourseOverview.assignment.getAssignmentNr());
        assignmentMap.put("AssignmentName", CourseOverview.assignment.getAssignmentName());
        assignmentMap.put("CourseKey", CourseOverview.assignment.getCourseKey());
        assignmentMap.put("difficulty", assignment.getAssignmentLevel());
        assignmentMap.put("hours", assignment.getAssignmentHours()+"");
        assignmentMap.put("email", LogIn.currentUser.getEmail());
        String joined = TextUtils.join(",", assignment.getResources());
        assignmentMap.put("resources", joined);
        DatabaseReference tester= mDatabase.child("Survey").push();
        tester.setValue(assignmentMap);




    }

    public void completeSurvey() {
        ArrayList<String> resources = new ArrayList<>();
        if (checkBox_lectures.isChecked()) {
            resources.add("lectures");
        }
        if (checkBox_youtube.isChecked()) { // must change name
            resources.add("videos");
        }
        if (checkBox_piazza.isChecked()) {
            resources.add("forums");
        }
        if (checkBox_stackOverflow.isChecked()) {
            resources.add("peers");
        }
        if (checkBox_other.isChecked()) {
            resources.add("other");
        }
        ArrayList<String> tracker = new ArrayList<>();
        for (String j : resources) {
            tracker.add(resources.get(resources.indexOf(j)));
        }
        if (tracker.isEmpty()) {
            Toast.makeText(Survey.this, "No resources selected", Toast.LENGTH_SHORT).show();
        } else {
            assignment.setResources(tracker);
        }
    }



    private void upDateAssignmentTime(){
        DatabaseReference userIDandCourseKey = mDatabase.child("StudentSubject").child(userID+" "+CourseOverview.assignment.getCourseKey());
        userIDandCourseKey.child("assignmentTime").child("assn "+CourseOverview.assignment.getAssignmentNr()).setValue(assignment.getAssignmentHours());

       // HashMap<String, Object> studCourses = (HashMap<String,Object>) dataSnapshot.getValue();
       // for (Object courseInfo: studCourses.values()) {    // Each key is random generated push(), each value = courseInfo
          //  Log.d(TAG, courseInfo + " = value for studCourses");

        //    HashMap<String, Object> course_info = (HashMap<String, Object>) courseInfo;  // Cast Object to HashMap
          //  Log.d(TAG, course_info + "");
            // courseInfo = {courskey = key, email = jakob, assignmentTime = {}}

            // If the assignmentTime table does not exist
         //   if((((HashMap<String, Object>) courseInfo).containsValue(LogIn.currentUser.getEmail())) && ((HashMap<String, Object>) courseInfo).containsValue(assignment.getCourseKey())){
               //if assignmentTime exists..


           //     Log.d(TAG, "emailer matcher!");
          //  }
            // Make new dictionary in the form of "{assignmentTime = {assn1 = 7}}"

            // If it is found, loop through assignmentTime.values()

            // HashMap<String,Object> value = (HashMap<String,Object>) value;
            // value = course_info.get("assignmentTime");
            // Cast value til hashmap og legg inn nytt entry jennom put.


            // If the email in a StudentSubject entry is the same as the currently logged in Student
            //  Log.d(TAG,"email: " + course_info.get("email") + ",  courseKey: " +course_info.get("courseKey"));

            //Not working, why?? Ask about objects.equals..
            //if ((Objects.equals(course_info.get("email"),LogIn.currentUser.getEmail())) && (Objects.equals(course_info.get("courseKey"), clickedCourse))){

        }



    //}


    private boolean hasAnswered(DataSnapshot dataSnapshot){
       // HashMap<String, Object> surveys =
     //  for (Object tracker : mDatabase.child("Survey").get){

      // }
        return false;
    }

    private void updateDB(){

    }
}


