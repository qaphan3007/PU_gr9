package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Konkurranse extends AppCompatActivity {
    private ImageView ImageView20;
    private ImageView ImageView19;
    private ImageView ImageView21;
    private ImageView ImageView22;
    private TextView textView18;
    private TextView textView26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konkurranse);
        click1();
        click2();
        click3();
        click4();
        click5();
        click6();
    }

    public void click1() {
        textView18 = (TextView) findViewById(R.id.textView18);
        textView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Lopet.class);
                startActivity(intent);
            }
        });
    }
    public void click6() {
        textView26 = (TextView) findViewById(R.id.textView26);
        textView26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NTNTU.class);
                startActivity(intent);
            }
        });
    }


    public void click2() {
        ImageView20 = (ImageView) findViewById(R.id.imageView20);
        ImageView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

    public void click3() {
        ImageView19 = (ImageView) findViewById(R.id.imageView19);
        ImageView19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click4() {
        ImageView21 = (ImageView) findViewById(R.id.imageView21);
        ImageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bemerkelser.class);
                startActivity(intent);
            }
        });
    }
    public void click5() {
        ImageView22 = (ImageView) findViewById(R.id.imageView22);
        ImageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }
}
