package com.example.lovek.lookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lovek.lookit.databaseClasses.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class scoreboard extends AppCompatActivity {

    private TextView row11;
    private TextView row12;
    private TextView row21;
    private TextView row22;
    private TextView row31;
    private TextView row32;
    private TextView row41;
    private TextView row42;
    private TextView row51;
    private TextView row52;
    private TextView row61;
    private TextView row62;
    private TextView row71;
    private TextView row72;
    private TextView row81;
    private TextView row82;

    private ProgressBar progressBar;

    //initialize a database reference to look at the "users" branch
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
    //array of the first 100 stored users in the database (if there is more than 100 users needs update)
    User[] users = new User[100];
    //counter for the users found
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        //get the users for the database with the attribute points
        mDatabase.orderByChild("points").limitToFirst(100).addChildEventListener(new ChildEventListener() {
            //look at all the users and store its data in a User object
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                users[count] = user;
                count++;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //show the progressbar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //wait two seconds to display the scoreboard
        //if we don't wait, we may not get all the values
        //so we have to wait for the firebase to end searching users
        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            public void run() {
                //once we wait go sort all the Users by their points and display them
                sortArray();
                //hide the progress bar
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private void sortArray() {
        boolean sorted = false;
        User userTmp = new User();

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < users.length - 1; i++) {
                //check if the user is not null
                //if we don't check it causes a Null pointer exception while trying to get the points
                if(users[i] == null || users[i+1] == null){
                    continue;
                }
                else {
                    //sort
                    if (users[i].points > users[i+1].points) {
                        sorted = false;
                        userTmp = users[i];
                        users[i] = users[i + 1];
                        users[i + 1] = userTmp;
                    }
                }
            }
        }
        //go printTable method
        printTable();
    }

    private void printTable() {
        //we check the count every time to know how many users we are going to display
        //currently is Top 8 scores (could be more if we update this part)
        //if we don't check a Null error happen
        if(count != 0) {
            row11 = (TextView) findViewById(R.id.row1_1);
            row11.setText(users[0].name);
            row12 = (TextView) findViewById(R.id.row1_2);
            row12.setText(String.valueOf(users[0].points));

            if (count > 1) {
                row21 = (TextView) findViewById(R.id.row2_1);
                row21.setText(users[1].name);
                row22 = (TextView) findViewById(R.id.row2_2);
                row22.setText(String.valueOf(users[1].points));

                if (count > 2) {
                    row31 = (TextView) findViewById(R.id.row3_1);
                    row31.setText(users[2].name);
                    row32 = (TextView) findViewById(R.id.row3_2);
                    row32.setText(String.valueOf(users[2].points));

                    if(count > 3) {
                        row41 = (TextView) findViewById(R.id.row4_1);
                        row41.setText(users[3].name);
                        row42 = (TextView) findViewById(R.id.row4_2);
                        row42.setText(String.valueOf(users[3].points));
                        if (count > 4) {
                            row51 = (TextView) findViewById(R.id.row5_1);
                            row51.setText(users[4].name);
                            row52 = (TextView) findViewById(R.id.row5_2);
                            row52.setText(String.valueOf(users[4].points));
                            if (count > 5) {
                                row61 = (TextView) findViewById(R.id.row6_1);
                                row61.setText(users[5].name);
                                row62 = (TextView) findViewById(R.id.row6_2);
                                row62.setText(String.valueOf(users[5].points));
                                if (count > 6) {
                                    row71 = (TextView) findViewById(R.id.row7_1);
                                    row71.setText(users[6].name);
                                    row72 = (TextView) findViewById(R.id.row7_2);
                                    row72.setText(String.valueOf(users[6].points));
                                    if (count > 7) {
                                        row81 = (TextView) findViewById(R.id.row8_1);
                                        row81.setText(users[7].name);
                                        row82 = (TextView) findViewById(R.id.row8_2);
                                        row82.setText(String.valueOf(users[7].points));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
