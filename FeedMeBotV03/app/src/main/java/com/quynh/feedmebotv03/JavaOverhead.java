package com.quynh.feedmebotv03;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class JavaOverhead extends AppCompatActivity{

    CheckBox checkBox;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_overhead);
    }

    public void goToFirstQuestion(View view) {


        final Button button = (Button) findViewById(R.id.button2);
        button.setTextColor(Color.GREEN);
        Intent intent = new Intent(this, FirstQuestion.class);
        startActivity(intent);
    }
}
