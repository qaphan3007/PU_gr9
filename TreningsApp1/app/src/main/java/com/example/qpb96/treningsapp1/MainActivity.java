package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView ImageView1;
    private ImageView ImageView2;
    private ImageView ImageView4;
    private ImageView ImageView3;
    private TextView  Textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        click();
        click1();
        click3();
        click4();
        click5();
    }
        public void click(){
        ImageView2 = (ImageView) findViewById(R.id.imageView2);
        ImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click1(){
        ImageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

    public void click3(){
        Textview = (TextView) findViewById(R.id.textView2);
        Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }

    public void click4(){
        ImageView3 = (ImageView) findViewById(R.id.imageView3);
        ImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bemerkelser.class);
                startActivity(intent);
            }
        });
    }

    public void click5(){
        ImageView4 = (ImageView) findViewById(R.id.imageView4);
        ImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }
}




