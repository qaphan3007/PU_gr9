package com.example.sterri.feedmebot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class JavaAssignment1 extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_assignment1);



        textView = (TextView) (findViewById(R.id.textView7));
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    textView.setText("Jeg kokte øvingen......");
                } else {
                    textView.setText(progress + " timer");
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
    public void goToJavaAssignment12(View view) {

        Intent intent = new Intent(this, JavaAssignment12.class);
        startActivity(intent);


    }

    public void goBack(View view) {

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);


    }

}
