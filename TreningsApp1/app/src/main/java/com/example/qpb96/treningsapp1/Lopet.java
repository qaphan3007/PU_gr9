package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Lopet extends AppCompatActivity {
    private ImageView ImageView24;
    private ImageView ImageView25;
    private ImageView ImageView26;
    private ImageView ImageView27;
    private ImageView ImageView99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lopet);
        click2();
        click3();
        click4();
        click5();
        click6();
    }

    public void click2() {
        ImageView25 = (ImageView) findViewById(R.id.imageView25);
        ImageView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

    public void click3() {
        ImageView24 = (ImageView) findViewById(R.id.imageView24);
        ImageView24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeMedKonk.class);
                startActivity(intent);
            }
        });
    }

    public void click4() {
        ImageView26 = (ImageView) findViewById(R.id.imageView26);
        ImageView26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bemerkelser.class);
                startActivity(intent);
            }
        });
    }
    public void click5() {
        ImageView27 = (ImageView) findViewById(R.id.imageView27);
        ImageView27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }

    public void click6() {
        ImageView99 = (ImageView) findViewById(R.id.imageView99);
        ImageView99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KartPluss.class);
                startActivity(intent);
            }
        });
    }
}
