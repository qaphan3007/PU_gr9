package com.example.sterri.feedmebot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

public class HomePageProfessor extends AppCompatActivity implements View.OnClickListener {

    private Button logOut;
    private Button userProfile;
    private Button courses;
    private Button feedbacks;
    private Button statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_professor);


        logOut = (Button) findViewById(R.id.button2);
        logOut.setOnClickListener( this);

        userProfile = (Button) findViewById(R.id.button10);
        userProfile.setOnClickListener( this);

        courses = (Button) findViewById(R.id.button12);
        courses.setOnClickListener( this);

        feedbacks = (Button) findViewById(R.id.button13);
        feedbacks.setOnClickListener( this);

        statistics = (Button) findViewById(R.id.button15);
        statistics.setOnClickListener( this);
    }

    public void onClick(View view) {
        if(view == logOut){
            //logging out the user
            //firebaseAuth.signOut();
            Toast.makeText(HomePageProfessor.this, "logging out...", Toast.LENGTH_SHORT).show();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        if(view == userProfile) {

            startActivity(new Intent(this, PUserProfile.class));
        }
        if(view == courses){

            startActivity(new Intent(this, PCourses.class));
        }
        if(view == feedbacks){

            startActivity(new Intent(this, PFeedBacks.class));
        }
        if(view == statistics){

            startActivity(new Intent(this, PStatistics.class));
        }



        }
    }

