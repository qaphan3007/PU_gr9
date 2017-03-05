package com.quynh.feedmebotv03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SecondQuestion extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    Assignment assignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_question);

        textView = (TextView) (findViewById(R.id.textView2));
        seekBar = (SeekBar) findViewById(R.id.seekBar2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    textView.setText("Soooo easy");
                } else if (progress == 1) {
                    textView.setText("Very easy");
                } else if (progress == 2) {
                    textView.setText("Easy");
                } else if (progress == 3) {
                    textView.setText("quite easy");
                } else if (progress == 4) {
                    textView.setText("Med");
                } else if (progress == 5) {
                    textView.setText("Med +");
                } else if (progress == 6) {
                    textView.setText("Med ++");
                } else if (progress == 7) {
                    textView.setText("Hard - ");
                }else if (progress == 8) {
                    textView.setText("Hard");
                }else if (progress == 9) {
                    textView.setText("Hard+");
                }else if (progress == 10) {
                    textView.setText("Hard ++++++");
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

    public void goToThirdQuestion(View view) {

        Intent intent = new Intent(this, ThirdQuestion.class);
        startActivity(intent);


    }
}
