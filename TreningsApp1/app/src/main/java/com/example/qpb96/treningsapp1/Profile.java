package com.example.qpb96.treningsapp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private ImageView ImageView1;
    private ImageView ImageView2;
    private ImageView ImageView4;
    private ImageView ImageView3;
    private ImageView ImageView90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        click();
        click1();
        click2();
        click3();
        click4();
        click5();
        click6();

    }

    public void click() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FinnVenner.class);
                startActivity(intent);
            }
        });
    }

    public void click1() {
        textView = (TextView) findViewById(R.id.textView7);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Per.class);
                startActivity(intent);
            }
        });
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

    public void click3() {
        ImageView2 = (ImageView) findViewById(R.id.imageView2);
        ImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void click4() {
        ImageView3 = (ImageView) findViewById(R.id.imageView3);
        ImageView3.setOnClickListener(new View.OnClickListener() {
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
}
