package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GulBemerkelse extends AppCompatActivity {

    private ImageView ImageView14;
    private ImageView ImageView15;
    private ImageView ImageView16;
    private ImageView ImageView17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gul_bemerkelse);
        click2();
        click3();
        click4();
        click5();
    }

    public void click2() {
        ImageView14 = (ImageView) findViewById(R.id.imageView15);
        ImageView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }

    public void click3() {
        ImageView15 = (ImageView) findViewById(R.id.imageView14);
        ImageView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click4() {
        ImageView16 = (ImageView) findViewById(R.id.imageView16);
        ImageView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bemerkelser.class);
                startActivity(intent);
            }
        });
    }
    public void click5() {
        ImageView17 = (ImageView) findViewById(R.id.imageView17);
        ImageView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }
}
