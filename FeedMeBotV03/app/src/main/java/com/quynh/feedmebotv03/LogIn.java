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

// This is the class that will run when "app" is run. It logs the student in (or registers).
// It uses the FirebaseAuth object to store the accounts, and each time there is a new user
// this is added as a (student) entry to the "UserInfo" child of our database.

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
    private DatabaseReference mDatabase;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();         //initializing firebase auth object

        // Initialize views and buttons
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonProf = (Button) findViewById(R.id.buttonProf);
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Get a reference to the Firebase auth object
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Signed in: " + user.getUid());
                    userID = user.getUid();

                } else {
                    Log.d(TAG, "Currently Signed Out");
                }
            }
        };


        //attaching listeners to buttons
        buttonSignup.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);
        buttonProf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(), ProfLogIn.class);
                startActivity(login);  // Move view to ProfLogIn
            }
        });

    }


    // make sure the Auth listener is active
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener );
    }

    // Remove the Auth listener when app is finished.
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // When user click on either login or register button, change FirebaseAuth user.
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


    // Check whether the user has typed in correct format in the input fields.
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


    // Sign user in via the Auth object.
    private void signInUserIn() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (checkValidInputField(email,password)) {
            //Sign the user in with email and password credentials
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        toastMessage("User is signed in.");
                        finish();
                        startActivity(new Intent(getApplicationContext(), ViewUserInfo.class));  // Move view to Profile
                    } else {
                        toastMessage("Failed to log in.");
                    }
                }
            });
        }
    }


    // Register new user with the Auth object, then add this new user entry to the database.
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
                        // Get the current logged in user
                        FirebaseUser user = mAuth.getCurrentUser();
                        userID = user.getUid();

                        // Make a new user entry in the database
                        HashMap<String, String> userEntry = new HashMap<String, String>();
                        userEntry.put("email", email);
                        userEntry.put("type", "student");
                        userEntry.put("name","null");
                        userEntry.put("phone", "null");
                        userEntry.put("birthday","null");

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
    public void goToSurvey(View view) {

        Intent intent = new Intent(this, Survey.class);
        startActivity(intent);
    }


    // Show message on the screen.
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}