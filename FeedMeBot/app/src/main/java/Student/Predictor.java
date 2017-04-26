package Student;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.quynh.feedmebot.CourseOverview;
import com.quynh.feedmebot.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import NonActivities.Assignment;
import Professor.ProfLogIn;

/**
 * Created by Quynh on 4/8/2017.
 */

public class Predictor extends AppCompatActivity {
    public static final String TAG = "Predictor";

    private EditText desiredGrade;
    private Button showPredictionButton;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private GraphView predictionGraph;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.predictor);

        desiredGrade = (EditText) findViewById(R.id.desiredGrade);
        showPredictionButton = (Button) findViewById(R.id.showPredictionButton);
        predictionGraph = (GraphView) findViewById(R.id.predictionGraph);
        predictionGraph.setVisibility(View.INVISIBLE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        showPredictionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String chosenGrade = desiredGrade.getText().toString().toUpperCase();

                myRef.child("StudentSubject").addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        showPrediction(chosenGrade,dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                predictionGraph.setVisibility(View.VISIBLE);   // Show graph after pressing the button
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPrediction(String desired_grade, DataSnapshot dataSnapshot) {
        HashMap<String,Object> studMap = (HashMap<String,Object>) dataSnapshot.getValue();

        // Make a list of all the subjects the student is attending
        ArrayList<Object> assignmentTimes = new ArrayList<>();
        // assignmentTimes = [ {assn1=4,assn2=5,assn3=4} , {assn1=5,assn2=2} , ...]

        for (Object courseInfo: studMap.values()){    // Each key is random generated push(), each value = courseInfo
            HashMap<String,Object> course_info = (HashMap<String,Object>) courseInfo;  // Cast Object to HashMap

            // If the grade of this student is the desiredGrade and the associated subject is the current subject
            if (Objects.equals(course_info.get("grade"),desired_grade) && Objects.equals(course_info.get("courseKey"), CourseOverview.assignment.getCourseKey())){
                // Add the assignmentTime.values() dict into the assignmentTimes ArrayList
                assignmentTimes.add(course_info.get("assignmentTime"));
            }
        }
        Log.d(TAG,"assignmentTimes list (dicts as entries): "+assignmentTimes.toString());
        // Calculate the average time used on each assignment then save it into two ArrayLists
        // where the first array represent the assignmentNr, the second the time used.
        ArrayList<Integer> assignment_nr = new ArrayList<>();
        ArrayList<Integer> total_hours = new ArrayList<>();
        ArrayList<Integer> entry_count = new ArrayList<>();   // Count how many students did each assignment

        for (Object dict_objects : assignmentTimes) {
            HashMap<String,Long> assnDict = (HashMap<String,Long>) dict_objects;  // Cast it into HashMap. Type Long bc errors.
            // assnDict = { assn1=5, assn2 = 4, assn3= 4}

            // First add the possible assignments in the assignment_nr array
            // Then add the average time to the total_hours array
            for (HashMap.Entry<String, Long> entry : assnDict.entrySet()) {
                // Key is a string with the format "assn 1". Split it by space to get only the number.
                Integer nr = Integer.parseInt(entry.getKey().split(" ")[1]);
                Integer value = Integer.valueOf(Long.toString(entry.getValue()));
                if (value != 0) {
                    if (!assignment_nr.contains(nr)) {  // If this is a new entry, add normally
                        assignment_nr.add(nr);
                        total_hours.add(value);
                        entry_count.add(0);
                    } else {       // else, set the new avg time value at the according index
                        Integer current_total_hours = total_hours.get(nr - 1);
                        total_hours.set(nr - 1, current_total_hours + value);   // Entry 1 has index 0
                        Integer current_entry_value = entry_count.get(nr - 1);
                        entry_count.set(nr - 1, current_entry_value + 1);
                    }
                }
            }
        }
        Log.d(TAG,"assignment nr: " + assignment_nr.toString());

        float[] avg_hours = new float[assignment_nr.size()];
        for (int i = 0; i < total_hours.size(); i++) {
            avg_hours[i] = (float) total_hours.get(i)/ (float) entry_count.get(i);
        }
        Log.d(TAG,"avg hours: " + avg_hours.toString());

        // Add the entries of the ordered arrays to the graph
        DataPoint[] points = new DataPoint[assignment_nr.size()+1];
        points[0] = new DataPoint(0,0);
        for (int i = 1; i < assignment_nr.size()+1; i++) {
            points[i] = new DataPoint(i,avg_hours[i-1]);
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points);

        series.setSpacing(30);   // Set spacing inbetween the x-entries as 30% of its width

        predictionGraph.addSeries(series);
        predictionGraph.getViewport().setMinY(0);

    }

}
