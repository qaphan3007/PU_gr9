package com.quynh.feedmebotv03;

/**
 * Created by qaphan3007 on 07.03.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// In this class, any changes made are activated by a button.
// After button press, all info should be changed in the database under
// the correct userID. (child of UserInfo)

public class EditProfile extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private DatabaseReference mUserInfoRef;

    private String userID;

    private Button editButton;
    private EditText nameEdit;
    private EditText majorEdit;
    private EditText birthdayEdit;
    private EditText phoneEdit;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_info);

        // Initialize layout objects
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        majorEdit = (EditText) findViewById(R.id.majorEdit);
        birthdayEdit = (EditText) findViewById(R.id.birthdayEdit);
        phoneEdit = (EditText) findViewById(R.id.phoneEdit);

        editButton = (Button) findViewById(R.id.edit);
        mUserInfoRef = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateData();
                Intent edit = new Intent(getApplicationContext(), Profile.class);
                startActivity(edit);  // Move view to User Profile
            }
        });
    }

    // This method gets the new values from the TextFields then update them in the database.
    private void updateData(){
        String name = nameEdit.getText().toString().trim();
        String major  = majorEdit.getText().toString().trim();
        String birthday  = birthdayEdit.getText().toString().trim();
        String phone  = phoneEdit.getText().toString().trim();

        if (!(name.equals(""))){
            mUserInfoRef.child(userID).child("name").setValue(name);
        }
        if (!(major.equals(""))){
            mUserInfoRef.child(userID).child("major").setValue(major);
        }
        if (!(birthday.equals(""))){
            mUserInfoRef.child(userID).child("birthday").setValue(birthday);
        }
        if (!(phone.equals(""))){
            mUserInfoRef.child(userID).child("phone").setValue(phone);
        }
    }

}
