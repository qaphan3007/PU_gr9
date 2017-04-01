package Professor;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.quynh.feedmebot.CourseOverview;
import com.quynh.feedmebot.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Quynh on 3/22/2017.
 */

public class Statistics extends AppCompatActivity {
    /** Example:
     * GraphView graph = (GraphView) findViewById(R.id.graph);
     BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
     new DataPoint(0, -1),
     new DataPoint(1, 5),
     new DataPoint(2, 3),
     new DataPoint(3, 2),
     new DataPoint(4, 6)
     });
     graph.addSeries(series);
     */
    public static final String TAG = "Statistics";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private GraphView assignmentTime;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        title = (TextView) findViewById(R.id.title);
        title.setText(CourseOverview.assignment.getAssignmentName());  // Set title of this page to the current Assignment's name

        assignmentTime = (GraphView) findViewById(R.id.assignmentTime);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


        myRef.child("StudentSubject").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called everytime there is a change to data
                // at this location.
                showAssignmentTime(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showAssignmentTime(DataSnapshot dataSnapshot) {
        HashMap<String, Object> studentSubjects = (HashMap<String, Object>) dataSnapshot.getValue();
        // studentSubjects = {generated_key = {infoMap1} , generated_key2 = {infoMap2}}, where each infoMap is:
        // infoMap1 = {coursekey = TDT4145, email = studmail@gmail.com, assignmentTime={dict}}
        HashMap<Integer, Integer> dataDict = new HashMap<>();   // dataDict = {time = stud_count, 1hour=5studs}

        // First we need to make a dataDict that will have info on how many students chose each possible Hour
        for (Object infoMaps : studentSubjects.values()) {    // studentSubjects.values() = {infoMap1, infoMap2}
            HashMap<String, Object> infoMap = (HashMap<String, Object>) infoMaps;  // infoMap = {coursekey = TDT4145, timedict={dict}}
            // If courseKey is the correct chosen courseKey
            if (Objects.equals(infoMap.get("courseKey"), CourseOverview.assignment.getCourseKey())) {
                HashMap<String, Object> timeDict = (HashMap<String, Object>) infoMap.get("assignmentTime"); // timedict={assNr=hour}
                for (Object assignmentNr : timeDict.keySet()) {    // Iterate thru the "assignmentTime" table's keys
                    String assignString = (String) assignmentNr;   // "assignString" = "assn 1"
                    String assignNr = assignString.split(" ")[1];  // get only the number part out
                    if (assignNr.equals(CourseOverview.assignment.getAssignmentNr())) {  // If we found the correct assignNr
                        Integer hour = Integer.valueOf(timeDict.get(assignString).toString());   // Get the hour
                        if (dataDict.containsKey(hour)) {
                            Integer old_count = dataDict.get(hour);
                            dataDict.put(hour, old_count + 1);   // if there is already an entry frm before, plus the count
                        } else {
                            dataDict.put(hour, 1);  // else make a new entry with stud_count = 1
                        }
                    }
                }
            }
        }
        Log.d(TAG,"datadict= " + dataDict.toString());
        if (dataDict.size() > 0) {      // If dataDict is not empty
            // Add the items in dataDict to our bargraph series now

            // Make sure that null-verdier are made into a (hour=0 answers) entry
            for (int index = 0;index<6;index++){
                if (!(dataDict.containsKey(index))){  // If there isnt an entry, make one
                    dataDict.put(index,0);
                }
            }
            /*
            // Datapoint (x,y): x is the hour, y is the antall studenter som har valg det

            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, dataDict.get(0)),
                    new DataPoint(1, dataDict.get(1)),
                    new DataPoint(2, dataDict.get(2)),
                    new DataPoint(3, dataDict.get(3)),
                    new DataPoint(4, dataDict.get(4)),
                    new DataPoint(5, dataDict.get(5))
            }); */

            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, 10),
                    new DataPoint(1, 5),
                    new DataPoint(2, 15),
                    new DataPoint(3, 24),
                    new DataPoint(4, 39),
                    new DataPoint(5, 2)
            });

            assignmentTime.addSeries(series);
            series.setSpacing(30);   // Set spacing inbetween the x-entries as 50% of its width
        }
    }
}