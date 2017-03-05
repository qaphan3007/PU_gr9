package com.example.sterri.feedmebot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

public class PUserProfile extends AppCompatActivity implements View.OnClickListener{
    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puser_profile);

        logOut = (Button) findViewById(R.id.button11);
        logOut.setOnClickListener( this);
    }

    @Override
    public void onClick(View view) {
        if(view==logOut) {
            //logging out the user
            //firebaseAuth.signOut();
            Toast.makeText(PUserProfile.this, "logging out...", Toast.LENGTH_SHORT).show();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
