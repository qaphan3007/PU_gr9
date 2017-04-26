package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NTNTU extends AppCompatActivity {
    private ImageView ImageView20;
    private ImageView ImageView19;
    private ImageView ImageView21;
    private ImageView ImageView22;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ntntu);


        click2();
        click3();
        click4();
        click5();
        click6();
    }
    public void click2() {
        ImageView20 = (ImageView) findViewById(R.id.imageView29);
        ImageView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

    public void click6() {
        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NTNU1.class);
                startActivity(intent);
            }
        });
    }

    public void click3() {
        ImageView19 = (ImageView) findViewById(R.id.imageView28);
        ImageView19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click4() {
        ImageView21 = (ImageView) findViewById(R.id.imageView30);
        ImageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bemerkelser.class);
                startActivity(intent);
            }
        });
    }
    public void click5() {
        ImageView22 = (ImageView) findViewById(R.id.imageView31);
        ImageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }
}
