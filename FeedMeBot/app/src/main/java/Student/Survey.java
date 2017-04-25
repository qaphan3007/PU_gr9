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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
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
    final String TAG = "test1233";


    //TODO: Må hente ut hvilket fag vi jobber med, hvilken øving og email til bruker.
    // en survey består av : assignmentNr, CourseKey, difficulty, feedback, rescources, og studmail.
    // mangler CourseKey, studmail. Kanskje lage getters i respektive klasser?

    // to get email : LogIn.currentUser.getEmail();



    // Initialize needed items
    TextView textViewTest;
    TextView textView;
    TextView textViewHour;
    SeekBar seekBarHour;
    SeekBar seekBarDifficultLevel;
    Button sendButton;
    CheckBox checkBox_youtube, checkBox_piazza, checkBox_lectures, checkBox_syllabus, checkBox_other,checkBox_stackOverflow;
    Assignment assignment = new Assignment();



    private DatabaseReference mDatabase;


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





        textViewHour = (TextView) (findViewById(R.id.textViewHour));
        seekBarHour = (SeekBar) findViewById(R.id.seekBarHours);
        seekBarHour.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    textViewHour.setText("Jeg kokte øvingen......"); // prints how many hours.
                    assignment.setAssignmentHours(progress); // Sets the the hours spent to the assignment object
                    if(assignment.getAssignmentHours() == 0) {
                        Toast.makeText(Survey.this, "Det funker!!!!! Og vi er awesome", Toast.LENGTH_SHORT).show();
                    }
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





       sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                completeSurvey(); //Gets resourcesfrom checkboxes
                updateData(); //Sends assignmentObject to DB.
                // On click sends all the data from assignment to the database

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

    public void completeSurvey(){
        ArrayList <String> resources = new ArrayList<>();
        if( checkBox_lectures.isChecked()){
            resources.add("lectures");
        }
        if(checkBox_youtube.isChecked()){ // must change name
            resources.add("videos");
        }
        if(checkBox_piazza.isChecked()){
            resources.add("forums");
        }
        if(checkBox_stackOverflow.isChecked()){
            resources.add("peers");
        }
        if(checkBox_other.isChecked()){
            resources.add("other");
        }
        ArrayList<String> tracker = new ArrayList<>();
        for (String j : resources){
            tracker.add(resources.get(resources.indexOf(j)));
        }
        if(tracker.isEmpty()){
            Toast.makeText(Survey.this, "No resources selected", Toast.LENGTH_SHORT).show();
        } else {
            assignment.setResources(tracker);
        }






    }
    private boolean hasAnswered(DataSnapshot dataSnapshot){
       // HashMap<String, Object> surveys =
     //  for (Object tracker : mDatabase.child("Survey").get){

      // }
        return false;
    }

    private void updateDB(){

    }
}


