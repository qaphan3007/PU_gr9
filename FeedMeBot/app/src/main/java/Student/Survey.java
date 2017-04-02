package Student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quynh.feedmebot.R;

import NonActivities.Assignment;

// Survey class is a common class with questions for all assignments in all subjects
public class Survey extends AppCompatActivity {
    final String TAG = "test1233";

    // Initialize needed items
    TextView textViewTest;
    TextView textView;
    TextView textViewHour;
    SeekBar seekBarHour;
    SeekBar seekBarDifficultLevel;
    Button sendButton;
    Assignment assignment;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        final Assignment assignment = new Assignment();
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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // On click sends all the data from assignment to the database
                updateData();
                // Then sends the user back to the previous page
            }
        });
    }

    private void updateData(){

    }
}


