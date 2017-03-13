package com.quynh.feedmebotv03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class FirstQuestion extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    Assignment assignment; // create an assignment object



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_question);
        textView = (TextView) (findViewById(R.id.textView));
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    textView.setText("Jeg kokte øvingen......");
                    assignment.setAssignmentHours(0);// sets the amount of time used in assignment object to 0
                } else {
                    textView.setText(progress + " timer");
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
    }


    public void goToSecondQuestion(View view) {

        Intent intent = new Intent(this, SecondQuestion.class);
        startActivity(intent);

    }
}

