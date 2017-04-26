package com.example.sterri.listviewtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String androidVersion;
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] androidVersions = {"Cupcake",
            "Donut",
            "Froyo",
            "KitKat",
            "Ice Cream"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, androidVersions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                androidVersion = parent.getItemAtPosition(position) + "";
                Toast.makeText(MainActivity.this, androidVersion, Toast.LENGTH_LONG).show();
                //Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected", Toast.LENGTH_LONG) .show(); ;
            }
        });
    }

    public void goToSecondActivity(View view) {

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}