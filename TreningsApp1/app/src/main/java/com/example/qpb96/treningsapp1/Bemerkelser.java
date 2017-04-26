package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Bemerkelser extends AppCompatActivity {

    private ImageView bem1;
    private ImageView ImageView10;
    private ImageView ImageView9;
    private ImageView ImageView11;
    private ImageView ImageView12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bemerkelser);
        click1();
        click2();
        click3();
        click4();
        click5();
    }

    public void click1() {
        bem1 = (ImageView) findViewById(R.id.imageViewaward);
        bem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GulBemerkelse.class);
                startActivity(intent);
            }
        });
    }

    public void click2() {
        ImageView10 = (ImageView) findViewById(R.id.imageView10);
        ImageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

    public void click3() {
        ImageView9 = (ImageView) findViewById(R.id.imageView9);
        ImageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click4() {
        ImageView11 = (ImageView) findViewById(R.id.imageView11);
        ImageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bemerkelser.class);
                startActivity(intent);
            }
        });
    }
    public void click5() {
        ImageView12 = (ImageView) findViewById(R.id.imageView12);
        ImageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }
}
