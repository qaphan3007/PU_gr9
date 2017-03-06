package com.quynh.feedmebotv03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by qaphan3007 on 05.03.2017.
 */

// This class is a copy of the Login.java class. It does everything the exact same way,
// except now it handles the professors' login.

public class ProfLogIn extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Professor Signing In";

    // Creating view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private Button buttonSignIn;
    private Button buttonStud;
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
        setContentView(R.layout.activity_prof_log_in);

        mAuth = FirebaseAuth.getInstance();         //initializing firebase auth object

        // Initialize views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonStud = (Button) findViewById(R.id.buttonStud);
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        buttonStud.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(), LogIn.class);
                startActivity(login);  // Move view to ProfLogIn
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

    private boolean checkValidInputField(String email, String password){
        //Check for valid email and password
        if(TextUtils.isEmpty(email)){
            toastMessage("Email field must not be empty.");
            return false;
        }
        else if(!email.contains("@")){
            toastMessage("Invalid email. (missing a @)");
            return false;
        }
        else if(TextUtils.isEmpty(password)){
            toastMessage("Password field must not be empty!");
            return false;

        }
        else if(password.length() < 10) {
            toastMessage("Password must at least contain 10 characters.");
            return false;
        }
        else {return true;}
    }


    private void signInUserIn() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (checkValidInputField(email,password)) {
            //Sign the user in with email and password credentials
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        toastMessage("User was logged in.");
                        finish();
                        //display some message here
                        startActivity(new Intent(getApplicationContext(), ViewUserInfo.class));  // Move view to Profile
                    } else {
                        toastMessage("Failed to log in.");
                    }
                }
            });
        }
    }


    private void registerUser(){
        //get email and password from the input field
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        if (checkValidInputField(email,password)) {

            // If the email and password are valid, display a progess log
            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            //creating a new user
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //checking if success
                    if (task.isSuccessful()) {
                        finish();

                        // Make a new user entry in the database
                        HashMap<String, String> userEntry = new HashMap<String, String>();
                        userEntry.put("email", email);
                        userEntry.put("type", "teacher");
                        mDatabase.child("UserInfo").child(userID).setValue(userEntry);  // Create key=userID & push the name,email under it

                        startActivity(new Intent(getApplicationContext(), ViewUserInfo.class));  // Move to Profile View
                    } else {
                        // Display the error message
                        toastMessage("Registration error.");
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}
