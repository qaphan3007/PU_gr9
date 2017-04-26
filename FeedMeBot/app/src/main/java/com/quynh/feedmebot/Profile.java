package com.quynh.feedmebot;

/**
 * Created by qaphan3007 on 05.03.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import Student.LogIn;
import NonActivities.User;

// This class shows all available userInfo under each userID as a list.

public class Profile extends AppCompatActivity {
    public static final String TAG = "Profile";

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private String userID;
    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private String var = ""; //Temp name, variable with wich listview clicked on
    public static String savedInfo;

    private Button editProfile;
    private Button courseOverview;
    private Button logOut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Save the chosen ListView item as a variable savedInfo
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                var = parent.getItemAtPosition(position) + ""; // Sets var equal to clicked listview
                String[] temp = var.split(":");     //splits string by ":"
                String savedInfo = temp[1].trim();
                Toast.makeText(Profile.this, savedInfo, Toast.LENGTH_LONG).show();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("UserInfo");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Signed in: " + user.getUid());
                } else {
                    Log.d(TAG, "Currently Signed Out");
                }
            }
        };


        // Every time there is a change to the database at myRef, we call showData to show the user info
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called everytime there is a change to data
                // at this location.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        // Jump to Edit Profile page
        editProfile = (Button) findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(edit);  // Move view to Edit Profile
            }
        });

        logOut = (Button) findViewById(R.id.log_out);
        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent logOut = new Intent(getApplicationContext(), LogIn.class);
                startActivity(logOut);  // Move view to LogIn
            }
        });

        courseOverview = (Button) findViewById(R.id.course_overview);
        courseOverview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent courses = new Intent(getApplicationContext(), CourseOverview.class);
                startActivity(courses);  // Move view to CourseOverview
            }
        });


    }



    private void showData(DataSnapshot dataSnapshot) {
        // userMap is a map that has the values: userID = HashMap of userInfo
        // (Example: MuwwNKM8COeZkaok8YPvtRdiSUi1 = {email=test123@gmail.com})
        HashMap<String,Object> userMap = (HashMap<String,Object>) dataSnapshot.getValue();

        for (String userID_key: userMap.keySet()){         // Iterate thru all keys to find correct userID
            if (userID_key.equals(userID)) {
                Object userinfo = userMap.get(userID);     // This is the map under the child with key = userID
                HashMap<String,Object> info = (HashMap<String,Object>) userinfo;  // Cast Object to HashMap

                // Save the values of the user info in a User object
                User uInfo = new User();
                uInfo.setEmail((String) info.get("email"));
                uInfo.setType((String) info.get("type"));
                uInfo.setName((String) info.get("name"));
                uInfo.setPhone((String) info.get("phone"));
                uInfo.setDate((String) info.get("birthday"));

                Log.d(TAG, "userInfo: email: " + uInfo.getEmail());
                Log.d(TAG, "userInfo: type: " + uInfo.getType());

                // Format the user info with correct label
                ArrayList<String> formatedUserInfo = new ArrayList<>();
                formatedUserInfo.add("Email:      " + uInfo.getEmail());
                formatedUserInfo.add("Type:       " + uInfo.getType());
                if (!uInfo.getName().equals("null")){
                    formatedUserInfo.add("Name:     " + uInfo.getName());
                } if (!uInfo.getPhone().equals("null")){
                    formatedUserInfo.add("Phone:    " + uInfo.getPhone());
                } if (!uInfo.getDate().equals("null")) {
                    formatedUserInfo.add("Birthday: " + uInfo.getDate());
                }

                // The ListView shows the items from the array formatedUserInfo using an adapter
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, formatedUserInfo);
                mListView.setAdapter(adapter);
            }
        }
    }

    //make sure the listener is active
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener );
    }

    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}
