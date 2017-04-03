package com.example.sterri.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ArrayList<String> resourceList = new ArrayList<>();
    TextView finalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        String androidVersion = MainActivity.androidVersion;
        TextView finalText = (TextView) findViewById(R.id.resourcePrint) ;
        finalText.setEnabled(false);
        Toast.makeText(SecondActivity.this, androidVersion, Toast.LENGTH_LONG).show();
    }

    public void selectItem(View view){

        boolean checked = ((CheckBox) view) .isChecked();

        switch (view.getId())
        {
            case R.id.Boka:

                if(checked){
                    resourceList.add("Boka");
                } else {
                    resourceList.remove("Boka");
                }
                break;

            case R.id.Youtube:

                if(checked){
                    resourceList.add("Youtube");
                } else {
                    resourceList.remove("Youtube");
                }
                break;

            case R.id.Piazza:

                if(checked){
                    resourceList.add("Piazza");
                } else {
                    resourceList.remove("Piazza");
                }
                break;

            case R.id.Other:

                if(checked){
                    resourceList.add("Other");
                } else {
                    resourceList.remove("Other");
                }
                break;

        }

    }



    public void printResources(View view){
           String allResources ="" ;

        for (String resources : resourceList){
            allResources = allResources + resources +"\n";
        }

        finalText.setText((String) allResources);
        finalText.setEnabled(true);
    }


}
