package com.example.sterri.feedmebot;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "User Signing";

    //Creating view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;

    private Button buttonSignIn;
    private ProgressDialog progressDialog;

    //defining firebaseauth object => Add auth members
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //Get a reference to the Firebase auth object
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "Signed in: " + user.getUid());
                }else{
                    Log.d(TAG, "Currently Signed Out");
                }
            }

        };


        //check if the user is already logged in
      // if(firebaseAuth.getCurrentUser() !=null){
            //if getCurrentUser is not null -> the user is already logged in
            //close this activity
        //  finish();

            //open profile activity
          //startActivity(new Intent(getApplicationContext(), HomePage.class));

        //}

        //init views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);


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
                        Toast.makeText(MainActivity.this, "user was logged in", Toast.LENGTH_SHORT).show();
                        finish();
                        //display some message here
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to log in", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    private void registerUser(){


        //get email and password from edittexts
        String email = editTextEmail.getText().toString().trim();
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

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            finish();
                            //display some message here
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        }else{
                            //display some message here
                            Toast.makeText(MainActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

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


    //  public void goToHomePage(View view) {
    //Intent intent = new Intent(this, HomePage.class);
    //startActivity(intent);
    //}


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
