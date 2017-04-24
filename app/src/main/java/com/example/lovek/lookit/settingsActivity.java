package com.example.lovek.lookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lovek.lookit.databaseClasses.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class settingsActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private boolean skipNull = false;
    private String tmpUsr = "12345abcdef";

    //declare where the user info is going to be displayed
    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;

    //declare Google user manager
    private GoogleApiClient googleApiClient;

    //declare Firebase authenticator and listener for Facebook and Google LogOut
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        idTextView = (TextView) findViewById(R.id.idTextView);

        //request the email to google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //enable the google user manager
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //check if there is a user
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //if we found a user set the data
                if (user != null){
                    setUserData(user);
                }
            }
        };

    }

    //get the info of the user
    //Use Glide to load the URL of the photo given by Firebase
    private void setUserData(FirebaseUser user) {
        nameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
        idTextView.setText(user.getUid());
        Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    //when this method is called go to the LogIn screen and add Flags to forget the last activity
    //if we don't add these flags the user can logOut and then go back to the game without login pressing the back button
    private void goLogInScreen() {
        Intent intent = new Intent (this, LogInActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //when the logout BUTTON is pressed
    public void logOut(View view) {
        //call firebase method to singout
        firebaseAuth.signOut();
        //GOOGLE logout
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLogInScreen();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error on log out", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //FACEBOOK logout
        LoginManager.getInstance().logOut();
        goLogInScreen();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //remove firebase listener
    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    //When change username button is clicked, get the current user form firebase
    public void changeUsername(View view) {
        FirebaseUser User2 = firebaseAuth.getCurrentUser();
        //call changeUserDisplayName method passing a Firebase user object
        changeUserDisplayName(User2);
    }

    private void changeUserDisplayName(final FirebaseUser user2) {
        //display the alert dialog to tell the user about the consequences of change his username
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(settingsActivity.this);
            alertDialogBuilder
                    .setMessage("You can't go back to your old username if you change it \nDo it anyway?")
                    //if they want to change his username anyway
                    .setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            View view = LayoutInflater.from(settingsActivity.this).inflate(R.layout.dialog_changeusername,null);

                            final EditText username = (EditText) view.findViewById(R.id.username);

                            //Display alert dialog to change username
                            AlertDialog.Builder builder = new AlertDialog.Builder(settingsActivity.this);

                            builder.setView(view)
                                    .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String u = username.getText().toString(); //get the input from the user
                                            if(u.equals(null) || u.equals("") || u.equals("0")){ //username can't be null, nothing or 0
                                                Toast.makeText(settingsActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                            else { //if they input a good looking username
                                                changeData(user2, u); //call changeDate method passing Firebase user object and input String from the user
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel",null)
                                    .setCancelable(false);
                            AlertDialog alert = builder.create();
                            alert.show();

                        }

                        //Use the update function of firebase to change UserDisplayName
                        private void changeData(final FirebaseUser user2, final String username) {
                            //reference the database to the "users" branch
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                            //look for the child under "users" with the User username
                            mDatabase.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //get the data found by the database
                                    //it is equal to null if its not founded
                                    User user = dataSnapshot.getValue(User.class);
                                    if(user == null){ //if not founded
                                        skipNull = false;                   //if we don't check if is null
                                    }                                       //we get a null exception error
                                                                            //when we try to check the value of user.name;
                                    else {  //if its founded
                                        skipNull = true;                    //we just can check if the User object is null
                                        tmpUsr = user.name;                 //if we try to check user.name we get null error
                                                                            //so if User = null, user.name is null too
                                    }
                                    //we check all the usernames in order to know if the username that we want is not
                                    //already in the database
                                    //if username is on the database and is not null (null when its a new username)
                                    if (tmpUsr.equals(username) && skipNull == true){
                                        Toast.makeText(settingsActivity.this, "Username already taken!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        //change user display name, requesting it to the firebase
                                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username)
                                                .build();
                                        user2.updateProfile(profileUpdate)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){ //if we could update the username
                                                            Toast.makeText(settingsActivity.this, "Username Updated!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        //logout and login automatically so changes are applied to your current play
                                        firebaseAuth.signOut();
                                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                                            @Override
                                            public void onResult(@NonNull Status status) {
                                                if(status.isSuccess()){
                                                    goLogInScreen();
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(), "Error on log out", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        //Facebook logout
                                        LoginManager.getInstance().logOut();
                                        goLogInScreen();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    })
                    .setCancelable(false)
                    .setNegativeButton("Cancel", null);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}