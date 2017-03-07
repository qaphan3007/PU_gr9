package com.quynh.feedmebotv03;

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
import java.util.Map;

// This class shows all available userInfo under each userID as a list.

public class ViewUserInfo extends AppCompatActivity {
    public static final String TAG = "ViewUserInfo";

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserInfoRef;

    private String userID;
    private ListView mListView;

    private Button editProfile;
    private Button logOut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_user_info);

        mListView = (ListView) findViewById(R.id.listview);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserInfoRef = mFirebaseDatabase.getReference().child("UserInfo");
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        // Jump to Edit Profile page
        editProfile = (Button) findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(edit);  // Move view to Edit Profile
            }
        });

        // Logs user out and jump back to the login page
        logOut = (Button) findViewById(R.id.log_out);
        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent edit = new Intent(getApplicationContext(), LogIn.class);
                startActivity(edit);  // Move view to LogIn
            }
        });

        // Every time there is a change to the database at mUserInfoRef, this is updated
        mUserInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called everytime there is a change to data
                // at this location.
                showData(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {     // Required method

            }
        });
    }


    // showData is the method that is called when there is a change to the database.
    // In this case, every time there is a new user
    private void showData(DataSnapshot dataSnapshot) {
        // userMap is a map that has the values: userID = HashMap of userInfo
        // (Example: MuwwNKM8COeZkaok8YPvtRdiSUi1 = {email=test123@gmail.com})
        HashMap<String,Object> userMap = (HashMap<String,Object>) dataSnapshot.getValue();

        for (String userID_key: userMap.keySet()){         // Iterate thru all keys to find correct userID
            if (userID_key.equals(userID)) {
                Object userinfo = userMap.get(userID);     // This is the map under the child with key = userID
                HashMap<String,Object> info = (HashMap<String,Object>) userinfo;  // Cast Object to HashMap

                // Add the needed items into a User object
                User uInfo = new User();
                uInfo.setEmail((String) info.get("email"));  // get the value of "email" from the HashMap
                uInfo.setType((String) info.get("type"));

                // Display the user info
                Log.d(TAG, "userInfo: email: " + uInfo.getEmail());
                Log.d(TAG, "userInfo: type: " + uInfo.getType());

                // Add the user info into an arrayList
                ArrayList<String> userInfoArray = new ArrayList<String>();
                userInfoArray.add(uInfo.getEmail());
                userInfoArray.add(uInfo.getType());

                // Set the userinfoArray into the ListView by using an adapter
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userInfoArray);
                mListView.setAdapter(adapter);
            }
        }
    }



}
