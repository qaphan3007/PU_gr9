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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
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
    private GraphView resourcesView;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        title = (TextView) findViewById(R.id.title);
        title.setText(CourseOverview.assignment.getAssignmentName());  // Set title of this page to the current Assignment's name

        assignmentTime = (GraphView) findViewById(R.id.assignmentTime);
        resourcesView = (GraphView) findViewById(R.id.resourcesView);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        // Make TextView here with the title "Assignment time"
        myRef.child("StudentSubject").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showAssignmentTime(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Make TextView here with the title "Resources used"
        myRef.child("Survey").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called everytime there is a change to data
                // at this location.
                showResourcesUsed(dataSnapshot);
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

        // First we need to make a dataDict that will have info on how many students chose each possible Hour
        HashMap<Integer, Integer> dataDict = new HashMap<>();   // dataDict = {time = stud_count, 1hour=5studs}

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
        if (dataDict.size() > 0) {      // If dataDict is not empty
            // Add the items in dataDict to our bargraph series now

            // Make sure that null-verdier are made into a (hour=0 answers) entry
            for (int index = 0;index<6;index++){
                if (!(dataDict.containsKey(index))){  // If there isnt an entry, make one
                    dataDict.put(index,0);
                }
            }
            BarGraphSeries<DataPoint> series;

            if (CourseOverview.assignment.getAssignmentNr().equals("1")) {  // Insert fake test values when assignNr = 1<
                 series= new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, 20),
                        new DataPoint(1, 15),
                        new DataPoint(2, 44),
                        new DataPoint(3, 32),
                        new DataPoint(4, 19)
                });
            } else {
                DataPoint[] points = new DataPoint[dataDict.size()];
                for (int i = 0; i < points.length; i++) {
                    points[i] = new DataPoint(i,dataDict.get(i));
                } series= new BarGraphSeries<>(points);
            }

            assignmentTime.addSeries(series);
            series.setSpacing(30);   // Set spacing inbetween the x-entries as 50% of its width

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showResourcesUsed(DataSnapshot dataSnapshot) {
        // surveys = {survey_id1 = {survey_answers1}, survey_id2 = {survey_answers2}, ...}
        // where each survey_answers = {assignmentNr = 1, courseKey = TDT4140, difficulty = "Very Easy",...}
        HashMap<String, Object> surveys = (HashMap<String, Object>) dataSnapshot.getValue();

        HashMap<String, Integer> resources = new HashMap<>();   // resources = {"youtube" = stud_count, "lectures"=5studs}

        for (Object survey_answer : surveys.values()) {    // surveys.values() = {survey_answers1, survey_answers2}
            HashMap<String, Object> survey_answers = (HashMap<String, Object>) survey_answer;  // Cast it back to HashMap

            // Read each objects and compare courseKey. If this is correct, read thru it.
            if (Objects.equals(survey_answers.get("courseKey"), CourseOverview.assignment.getCourseKey())) {
                // resource_dict = {rdm_key = youtube, key2 = lecture}
                HashMap<String, Object> resource_dict = (HashMap<String, Object>) survey_answers.get("resources");

                for (Object each_resource : resource_dict.values()) {   // resource_values = ["youtube", "lectures",..}
                    String resource = (String) each_resource;    // resource is for example "youtube"

                    if (resources.containsKey(resource)) {
                        Integer old_count = resources.get(resource);
                        resources.put(resource, old_count + 1);   // if there is already an entry from before, plus the count
                    } else {
                        resources.put(resource, 1);  // else make a new entry with stud_count = 1
                    }
                }
            }
        }

        // By now our resources dict should look like {"Youtube" = 5, "Lectures" = 50, etc.}
        if (resources.size() > 0) {      // If resources dict is not empty
            // We will now add the items in resources dict to our bargraph series to showcase them.

            // Make sure that null-verdier are made into a (resource=0answers) entry
            // iterate through a list of all available resources to put resource=0studsUsed
            String[] available_resources = {"Videos","Peers", "Lectures","Forums", "Others"};
            // String[] available_resources = {"Videos","Lectures", "Forums", "Others"};

            for (int index = 0;index<available_resources.length;index++){
                if (!(resources.containsKey(available_resources[index].toLowerCase()))){  // If there isnt an entry, make one
                    resources.put(available_resources[index].toLowerCase(),0);   // Ex: available_resources[0] = "Youtube"
                }
            }
            Log.d(TAG,"final resources dict: "+ resources.toString());


            // Generate the series to display through a bar graph.
            BarGraphSeries<DataPoint> series;

            if (CourseOverview.assignment.getAssignmentNr().equals("1")) {  // Insert fake test values when assignNr = 1<
                series = new BarGraphSeries<>(new DataPoint[]{
                        new DataPoint(0, 20),
                        new DataPoint(1, 15),
                        new DataPoint(2, 44),
                        new DataPoint(3, 32),
                        new DataPoint(4, 19),
                });
            } else {
                DataPoint[] points = new DataPoint[resources.size()];
                for (int i = 0; i < points.length; i++) {
                    points[i] = new DataPoint(i,resources.get(available_resources[i].toLowerCase()));
                } series= new BarGraphSeries<>(points);
            }

            resourcesView.addSeries(series);
            series.setSpacing(30);   // Set spacing inbetween the x-entries as 30% of its width

            // use static labels for horizontal labels
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(resourcesView);
            staticLabelsFormatter.setHorizontalLabels(available_resources);
            resourcesView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            //resourcesView.getGridLabelRenderer().setVerticalAxisTitle("Number of students");

            resourcesView.getGridLabelRenderer().setHorizontalAxisTitle("Resource used");
            resourcesView.getGridLabelRenderer().setLabelHorizontalHeight(20);
            //resourcesView.getGridLabelRenderer().setLabelVerticalWidth(20);

            // set manual x bounds to have nice steps
            resourcesView.getViewport().setMinX(0);
            resourcesView.getViewport().setMaxX(4);
            resourcesView.getViewport().setXAxisBoundsManual(true);

            resourcesView.getViewport().setScalable(true); // enables horizontal zooming and scrolling

        }
    }
}
