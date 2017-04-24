package com.example.lovek.lookit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lovek.lookit.MainActivity;
import com.example.lovek.lookit.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //Google Login button
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;

    //firebase token code
    public static final int SIGN_IN_CODE = 777;

    //firebase caller and listener
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    //progress bar animation when sign in
    private ProgressBar progressBar;

    //images of Login activity
    private ImageView Lookit;
    private ImageView welcome;

    //Facebook Login button
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //GOOGLE
        //request to Google the Email and unique Id of the user
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //initialize the google Login button
        signInButton = (SignInButton) findViewById(R.id.signInButton);
        //gives wider aspect to the button
        signInButton.setSize(SignInButton.SIZE_WIDE);

        //when the Google button is clicked, open the personalized google Signin screen
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    goMainScreen();
                }
            }
        };

        //initialize the ImageViews
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Lookit = (ImageView) findViewById(R.id.imageView3);
        welcome = (ImageView) findViewById(R.id.imageView4);

        //FACEBOOK
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);

        //get Permission to use the email from facebook
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.setReadPermissions(Arrays.asList("user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            //IF THE REGISTRATION WITH FACEBOOK IS:
            @Override
            //SUCCES: get the facebook token to authenticate the user with firebase then go Main screen
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccesToken(loginResult.getAccessToken());
                goMainScreen();
            }

            @Override
            //CANCELLED:
            public void onCancel() {
                Toast.makeText(LogInActivity.this, "The process was canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            //INVALID
            public void onError(FacebookException error) {
                Toast.makeText(LogInActivity.this, "Error when LogIn", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //Get the FacebookAccesToken from Firebase
    private void handleFacebookAccesToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        //hide the elements in the activity and show the progress bar
        progressBar.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);
        Lookit.setVisibility(View.GONE);
        welcome.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);

        //when we have the token we logIn with firebae
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //hide the progress bar and show the activities
                progressBar.setVisibility(View.GONE);
                signInButton.setVisibility(View.VISIBLE);
                Lookit.setVisibility(View.VISIBLE);
                welcome.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);

                //if we cannot to firebase send a message
                if (!task.isSuccessful()){
                    Toast.makeText(LogInActivity.this, "Firebase LogIn Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Firebase Listener begins
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check if the request code returned by Google is the same
        //if is the same get the SignIn result and pass it to handleSignIn Result method
        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    //check if the signin is succesful
    private void handleSignInResult(GoogleSignInResult result) {
        //if we can sign in with google call the method to authenticate it with firebase
        if (result.isSuccess()){
            firebaseAuthWithGoogle(result.getSignInAccount());
        } //else display a error message
        else{
            Toast.makeText(this, "Error when login", Toast.LENGTH_LONG).show();
        }
    }

    //pass the GOOGLE credentials to login to FIREBASE
    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        //while the database is getting the data display the progressbar animation and
        //hide the other ImagesView
        progressBar.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);
        Lookit.setVisibility(View.GONE);
        welcome.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //show the logInScreen without progressbar
                progressBar.setVisibility(View.GONE);
                signInButton.setVisibility(View.VISIBLE);
                Lookit.setVisibility(View.VISIBLE);
                welcome.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);


                //if the authentication is not succesful show a Error message
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error when log in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //change the activity to the main screen
    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //when the activity closes remove firebase Listener
    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){

    }
}
