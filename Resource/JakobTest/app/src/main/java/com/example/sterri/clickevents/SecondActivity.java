package com.example.sterri.clickevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    int ovingsTid;

    public void kok(View view){
        ovingsTid=-1;
    }
    public void forste(View view){
        ovingsTid=1;
    }
    public void andre(View view){
        ovingsTid=2;
    }
    public void tredje(View view){
        ovingsTid=3;
    }
    public void next(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
