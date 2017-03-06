package com.quynh.feedmebotv03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

// This is the class that will run when "app" is run. It logs the STUDENT in (or registers).

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Student Signing In";

    // Creating view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private Button buttonSignIn;
    private Button buttonProf;
    private ProgressDialog progressDialog;

    // Defining firebaseauth object => Add auth members
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();         //initializing firebase auth object

        // Initialize views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonProf = (Button) findViewById(R.id.buttonProf);
        progressDialog = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();   // Ref to Database
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        //Get a reference to the Firebase auth object
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Signed in: " + user.getUid());
                    toastMessage("Successfully signed in with: "+ user.getEmail());
                    userID = user.getUid();

                } else {
                    Log.d(TAG, "Currently Signed Out");
                    toastMessage("Successfully signed out.");
                }
            }
        };

        mDatabase.child("UserInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // When there is change to the database, log it. (save it)
                Log.d("Adding Value", "onDataChange: Added info to database: \n" +
                dataSnapshot.getValue());   // Value of this is going to be the key
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Adding Value","Failed to add value.", databaseError.toException());
            }
        });

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);
        buttonProf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent profLog = new Intent(getApplicationContext(), ProfLogIn.class);
                startActivity(profLog);  // Move view to ProfLogIn
            }
        });

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


    @Override
    public void onClick(View view) {
        if (view == buttonSignup) {
            //calling register method on click
            registerUser();
        }
        if (view == buttonSignIn) {
            //open login activity when user taps on the already registered textview
            signInUserIn();
        }
    }


    private void signInUserIn() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        //Sign the user in with email and password credentials
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LogIn.this, "user was logged in", Toast.LENGTH_SHORT).show();
                    finish();
                    //display some message here
                    startActivity(new Intent(getApplicationContext(), ViewUserInfo.class));  // Move view to HomePage
                } else {
                    Toast.makeText(LogIn.this, "Failed to log in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void registerUser(){
        //get email and password from edittexts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(!email.contains("@")){
            Toast.makeText(this,"Invalid Email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;

        }
        if(password.length() < 10) {
            Toast.makeText(this, "Password must at least contain 10 characters", Toast.LENGTH_LONG).show();
            return;
        }

        // If the email and password are not empty
        // display a progress dialog
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //checking if success
                if(task.isSuccessful()){
                    finish();

                    // Make a new user entry in the database
                    HashMap<String,String> userEntry = new HashMap<String,String>();
                    userEntry.put("email",email);   // userEntry = { email = email, type=student}
                    userEntry.put("type","student");
                    mDatabase.child("UserInfo").child(userID).setValue(userEntry);  // Create key=userID & push the name,email under it

                    startActivity(new Intent(getApplicationContext(), ViewUserInfo.class));  // Move to View User Profile
                }else{
                    // Display tddddhe error message
                    Toast.makeText(LogIn.this,"Registration Error",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });

    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}


