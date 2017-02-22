package com.example.sterri.feedmebot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class JavaOversikt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_oversikt);


    }

    public void goToJavaAssignment1(View view) {

        Intent intent = new Intent(this, JavaAssignment1.class);
        startActivity(intent);


    }
}
