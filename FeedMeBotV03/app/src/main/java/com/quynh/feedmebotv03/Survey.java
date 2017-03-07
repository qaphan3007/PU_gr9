package com.quynh.feedmebotv03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
// Survey class is a common class with questions for all assignments in all subjects
public class Survey extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    SeekBar seekBarDifficultLevel;
    Assignment assignment; // create an assignment object@

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        textView = (TextView) (findViewById(R.id.textViewHour));

        seekBar = (SeekBar) findViewById(R.id.seekBarHours);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    textView.setText("Jeg kokte øvingen......");
                    assignment.setAssignmentHours(0);// sets the amount of time used in assignment object to 0
                } else {
                    textView.setText(progress + " hours");
                    assignment.setAssignmentHours(progress); // sets the amount of time used in assignment object
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarDifficultLevel = (SeekBar) findViewById(R.id.seekBarDifficultLevel);
        seekBarDifficultLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}


