package com.quynh.feedmebotv03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }
    public void goToSubject(View view) {

        Intent intent = new Intent(this, JavaOverhead.class);
        startActivity(intent);
    }
}
