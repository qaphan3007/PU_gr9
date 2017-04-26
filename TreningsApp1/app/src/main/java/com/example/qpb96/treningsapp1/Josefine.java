package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Josefine extends AppCompatActivity {

    private ImageView ImageView6;
    private ImageView ImageView1;
    private ImageView ImageView5;
    private ImageView ImageView4;
    private ImageView ImageView90;
    private ImageView ImageView91;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_josefine);

        click2();
        click3();
        click4();
        click5();

        click8();
    }

    public void click2() {
        ImageView1 = (ImageView) findViewById(R.id.imageView33);
        ImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }



    public void click3() {
        ImageView6 = (ImageView) findViewById(R.id.imageView32);
        ImageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click4() {
        ImageView5 = (ImageView) findViewById(R.id.imageView33);
        ImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bemerkelser.class);
                startActivity(intent);
            }
        });
    }

    public void click5() {
        ImageView4 = (ImageView) findViewById(R.id.imageView34);
        ImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }




    public void click8() {
        button2 = (Button) findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Josfine1.class);
                startActivity(intent);
            }
        });
    }}

