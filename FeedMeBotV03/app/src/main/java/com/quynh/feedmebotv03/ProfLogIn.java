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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProfLogIn extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "User Signing";

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
    private User user_class;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_login);
        user_class = new User();

        mAuth = FirebaseAuth.getInstance();         //initializing firebase auth object

        //Get a reference to the Firebase auth object
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Signed in: " + user.getUid());
                } else {
                    Log.d(TAG, "Currently Signed Out");
                }
            }
        };


        // Initialize views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonStud = (Button) findViewById(R.id.buttonProf);
        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);
        buttonStud.setOnClickListener(this);

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
                    Toast.makeText(ProfLogIn.this, "user was logged in", Toast.LENGTH_SHORT).show();
                    finish();
                    //display some message here
                    startActivity(new Intent(getApplicationContext(), HomePage.class));  // Move view to HomePage
                } else {
                    Toast.makeText(ProfLogIn.this, "Failed to log in", Toast.LENGTH_SHORT).show();
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

        // Ff the email and password are not empty
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
                    //display success message here

                    // Make a new user entry in the database
                    HashMap<String,String> userEntry = new HashMap<String,String>();
                    userEntry.put("email",email);
                    userEntry.put("type","teacher");
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("UserInfo").push().setValue(userEntry);  // Create a random key & push the name,email under it

                    user_class.setEmail(email);

                    startActivity(new Intent(getApplicationContext(), HomePage.class));  // Move to HomePage
                }else{
                    //display error message here
                    Toast.makeText(ProfLogIn.this,"Registration Error",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
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

}
