package com.quynh.feedmebotv03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ThirdQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_question);
    }

    public void goToHomePage(View view) {

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);


    }
}
