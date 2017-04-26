package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Anne extends AppCompatActivity {


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
        setContentView(R.layout.activity_anne);

        click2();
        click3();
        click4();
        click5();
        click6();
        click7();
        click8();
    }

    public void click2() {
        ImageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
    }
    public void click7() {
        ImageView91 = (ImageView) findViewById(R.id.imageView91);
        ImageView91.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Medal1.class);
                startActivity(intent);
            }
        });
    }

    public void click3() {
        ImageView6 = (ImageView) findViewById(R.id.imageView6);
        ImageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click4() {
        ImageView5 = (ImageView) findViewById(R.id.imageView5);
        ImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Bemerkelser.class);
                startActivity(intent);
            }
        });
    }

    public void click5() {
        ImageView4 = (ImageView) findViewById(R.id.imageView4);
        ImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Konkurranse.class);
                startActivity(intent);
            }
        });
    }

    public void click6() {
        ImageView90 = (ImageView) findViewById(R.id.imageView90);
        ImageView90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GulBemerkelse.class);
                startActivity(intent);
            }
        });
    }


    public void click8() {
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Anne1.class);
                startActivity(intent);
            }
        });
    }
}
