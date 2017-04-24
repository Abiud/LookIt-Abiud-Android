package com.example.lovek.lookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lovek.lookit.databaseClasses.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.RunnableFuture;

public class mediumSingle extends AppCompatActivity {

    private TextView timer;
    private Handler handler;
    private long timePassed;
    private long init = System.currentTimeMillis();
    private long now;
    private String timeDisplay;
    private boolean continueT = true;
    private Double finalPoints = 0.0;
    private Double pastRecord = 0.0;
    private boolean databaseRecord = false;

    TextView tv_p1;
    ImageView iv_11,iv_12,iv_13,iv_14, iv_15,
            iv_21,iv_22,iv_23,iv_24, iv_25,
            iv_31,iv_32,iv_33,iv_34, iv_35,
            iv_41,iv_42,iv_43,iv_44, iv_45;

    //array for the images
    Integer[] cardAr={101,102,103,104,105,106,107,108,109,110,
            201,202,203,204,205,206,207,208,209,210};

    //actual images
    int image101, image102, image103, image104, image105, image106,image107, image108, image109, image110,
            image201, image202,image203,image204,image205,image206, image207, image208, image209, image210;

    int firstC, secC;
    int clickedF, clickedS;
    int cardNum=1;

    int playerPts=0;

    //initialize a database reference to look at the "users" branch
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

    //get the user currently playing
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_single);

        //declare the timer
        timer = (TextView) findViewById(R.id.timer);

        //handler update the timer TextView every 30 nanoseconds
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                now = System.currentTimeMillis(); //get the current time
                timeDisplay = String.format("%1$.1f", (now-init)/1000.0); //get the timed passed between the begining of the activity and "now"
                timer.setText(String.valueOf(timeDisplay)); //update timer TextView
                timePassed = now - init; //store the time passed in the variable timePassed
                if(timePassed < 200000 && continueT != false) { //if the timePassed is less than 3.33 min and the user dont win
                    handler.postDelayed(this, 30); //update
                }
            }
        };
        handler.postDelayed(runnable, 30);


        tv_p1=(TextView) findViewById(R.id.tv_p1);

        iv_11=(ImageView) findViewById(R.id.iv_11);
        iv_12=(ImageView) findViewById(R.id.iv_12);
        iv_13=(ImageView) findViewById(R.id.iv_13);
        iv_14=(ImageView) findViewById(R.id.iv_14);
        iv_15=(ImageView) findViewById(R.id.iv_15);
        iv_21=(ImageView) findViewById(R.id.iv_21);
        iv_22=(ImageView) findViewById(R.id.iv_22);
        iv_23=(ImageView) findViewById(R.id.iv_23);
        iv_24=(ImageView) findViewById(R.id.iv_24);
        iv_25=(ImageView) findViewById(R.id.iv_25);
        iv_31=(ImageView) findViewById(R.id.iv_31);
        iv_32=(ImageView) findViewById(R.id.iv_32);
        iv_33=(ImageView) findViewById(R.id.iv_33);
        iv_34=(ImageView) findViewById(R.id.iv_34);
        iv_35=(ImageView) findViewById(R.id.iv_35);
        iv_41=(ImageView) findViewById(R.id.iv_41);
        iv_42=(ImageView) findViewById(R.id.iv_42);
        iv_43=(ImageView) findViewById(R.id.iv_43);
        iv_44=(ImageView) findViewById(R.id.iv_44);
        iv_45=(ImageView) findViewById(R.id.iv_45);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_15.setTag("4");
        iv_21.setTag("5");
        iv_22.setTag("6");
        iv_23.setTag("7");
        iv_24.setTag("8");
        iv_25.setTag("9");
        iv_31.setTag("10");
        iv_32.setTag("11");
        iv_33.setTag("12");
        iv_34.setTag("13");
        iv_35.setTag("14");
        iv_41.setTag("15");
        iv_42.setTag("16");
        iv_43.setTag("17");
        iv_44.setTag("18");
        iv_45.setTag("19");

        //load the card images
        frontOfCardsResources();

        //shuffle the images
        Collections.shuffle(Arrays.asList(cardAr));

        //changing the color of the second player
        tv_p1.setTextColor(Color.WHITE);

        iv_11.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_11, theCard);
            }
        });

        iv_12.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_12, theCard);
            }
        });

        iv_13.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_13, theCard);
            }
        });

        iv_14.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_14, theCard);
            }
        });

        iv_15.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_15, theCard);
            }
        });

        iv_21.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_21, theCard);
            }
        });

        iv_22.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_22, theCard);
            }
        });

        iv_23.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_23, theCard);
            }
        });

        iv_24.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_24, theCard);
            }
        });

        iv_25.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_25, theCard);
            }
        });

        iv_31.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_31, theCard);
            }
        });

        iv_32.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_32, theCard);
            }
        });

        iv_33.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_33, theCard);
            }
        });

        iv_34.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_34, theCard);
            }
        });

        iv_35.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_35, theCard);
            }
        });

        iv_41.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_41, theCard);
            }
        });

        iv_42.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_42, theCard);
            }
        });

        iv_43.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_43, theCard);
            }
        });

        iv_44.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_44, theCard);
            }
        });

        iv_45.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int theCard=Integer.parseInt((String)view.getTag());
                doStuff(iv_45, theCard);
            }
        });

    }


    private void doStuff(ImageView iv, int card)
    {
        if(cardAr[card]==101)
        {
            iv.setImageResource(image101);
        }
        else if (cardAr[card]==102)
        {
            iv.setImageResource(image102);
        }
        else if (cardAr[card]==103)
        {
            iv.setImageResource(image103);
        }
        else if (cardAr[card]==104)
        {
            iv.setImageResource(image104);
        }
        else if (cardAr[card]==105)
        {
            iv.setImageResource(image105);
        }
        else if (cardAr[card]==106)
        {
            iv.setImageResource(image106);
        }
        else if (cardAr[card]==107)
        {
            iv.setImageResource(image107);
        }
        else if (cardAr[card]==108)
        {
            iv.setImageResource(image108);
        }
        else if (cardAr[card]==109)
        {
            iv.setImageResource(image109);
        }
        else if (cardAr[card]==110)
        {
            iv.setImageResource(image110);
        }
        else if (cardAr[card]==201)
        {
            iv.setImageResource(image201);
        }
        else if (cardAr[card]==202)
        {
            iv.setImageResource(image202);
        }
        else if (cardAr[card]==203)
        {
            iv.setImageResource(image203);
        }
        else if (cardAr[card]==204)
        {
            iv.setImageResource(image204);
        }
        else if (cardAr[card]==205)
        {
            iv.setImageResource(image205);
        }
        else if (cardAr[card]==206)
        {
            iv.setImageResource(image206);
        }
        else if (cardAr[card]==207)
        {
            iv.setImageResource(image207);
        }
        else if (cardAr[card]==208)
        {
            iv.setImageResource(image208);
        }
        else if (cardAr[card]==209)
        {
            iv.setImageResource(image209);
        }
        else if (cardAr[card]==210)
        {
            iv.setImageResource(image210);
        }

        //check which image is selected and save it to temporary variable
        if(cardNum==1)
        {
            firstC=cardAr[card];
            if(firstC>200)
            {
                firstC=firstC-100;
            }
            cardNum=2;
            clickedF=card;

            iv.setEnabled(false);
        } else if(cardNum==2)
        {
            secC=cardAr[card];
            if(secC>200)
            {
                secC=secC-100;
            }

            cardNum=1;
            clickedS=card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_15.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_25.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);
            iv_35.setEnabled(false);
            iv_41.setEnabled(false);
            iv_42.setEnabled(false);
            iv_43.setEnabled(false);
            iv_44.setEnabled(false);
            iv_45.setEnabled(false);

            Handler handler=new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    //check if selected images are equal
                    calculate();
                }
            }, 1000);
        }
    }
    private void frontOfCardsResources()
    {
        image101=R.drawable.image101;
        image102=R.drawable.image102;
        image103=R.drawable.image103;
        image104=R.drawable.image104;
        image105=R.drawable.image105;
        image106=R.drawable.image106;
        image107=R.drawable.image107;
        image108=R.drawable.image108;
        image109=R.drawable.image109;
        image110=R.drawable.image110;
        image201=R.drawable.image201;
        image202=R.drawable.image202;
        image203=R.drawable.image203;
        image204=R.drawable.image204;
        image205=R.drawable.image205;
        image206=R.drawable.image206;
        image207=R.drawable.image207;
        image208=R.drawable.image208;
        image209=R.drawable.image209;
        image210=R.drawable.image210;
    }

    private void calculate()
    {
        //if images are equal, remove them and add point
        if(firstC==secC)
        {
            if(clickedF==0)
            {
                iv_11.setVisibility(View.INVISIBLE);
            } else if(clickedF==1){
                iv_12.setVisibility(View.INVISIBLE);
            }else if(clickedF==2){
                iv_13.setVisibility(View.INVISIBLE);
            }else if(clickedF==3){
                iv_14.setVisibility(View.INVISIBLE);
            }else if(clickedF==4){
                iv_15.setVisibility(View.INVISIBLE);
            }else if(clickedF==5){
                iv_21.setVisibility(View.INVISIBLE);
            }else if(clickedF==6){
                iv_22.setVisibility(View.INVISIBLE);
            }else if(clickedF==7){
                iv_23.setVisibility(View.INVISIBLE);
            }else if(clickedF==8){
                iv_24.setVisibility(View.INVISIBLE);
            }else if(clickedF==9){
                iv_25.setVisibility(View.INVISIBLE);
            }else if(clickedF==10){
                iv_31.setVisibility(View.INVISIBLE);
            }else if(clickedF==11){
                iv_32.setVisibility(View.INVISIBLE);
            }else if(clickedF==12){
                iv_33.setVisibility(View.INVISIBLE);
            }else if(clickedF==13){
                iv_34.setVisibility(View.INVISIBLE);
            }else if(clickedF==14){
                iv_35.setVisibility(View.INVISIBLE);
            }else if(clickedF==15){
                iv_41.setVisibility(View.INVISIBLE);
            }else if(clickedF==16){
                iv_42.setVisibility(View.INVISIBLE);
            }else if(clickedF==17){
                iv_43.setVisibility(View.INVISIBLE);
            }else if(clickedF==18){
                iv_44.setVisibility(View.INVISIBLE);
            }else if(clickedF==19){
                iv_45.setVisibility(View.INVISIBLE);
            }

            if(clickedS==0)
            {
                iv_11.setVisibility(View.INVISIBLE);
            } else if(clickedS==1){
                iv_12.setVisibility(View.INVISIBLE);
            }else if(clickedS==2){
                iv_13.setVisibility(View.INVISIBLE);
            }else if(clickedS==3){
                iv_14.setVisibility(View.INVISIBLE);
            }else if(clickedS==4){
                iv_15.setVisibility(View.INVISIBLE);
            }else if(clickedS==5){
                iv_21.setVisibility(View.INVISIBLE);
            }else if(clickedS==6){
                iv_22.setVisibility(View.INVISIBLE);
            }else if(clickedS==7){
                iv_23.setVisibility(View.INVISIBLE);
            }else if(clickedS==8){
                iv_24.setVisibility(View.INVISIBLE);
            }else if(clickedS==9){
                iv_25.setVisibility(View.INVISIBLE);
            }else if(clickedS==10){
                iv_31.setVisibility(View.INVISIBLE);
            }else if(clickedS==11){
                iv_32.setVisibility(View.INVISIBLE);
            }else if(clickedS==12){
                iv_33.setVisibility(View.INVISIBLE);
            }else if(clickedS==13){
                iv_34.setVisibility(View.INVISIBLE);
            }else if(clickedS==14){
                iv_35.setVisibility(View.INVISIBLE);
            }else if(clickedS==15){
                iv_41.setVisibility(View.INVISIBLE);
            }else if(clickedS==16){
                iv_42.setVisibility(View.INVISIBLE);
            }else if(clickedS==17){
                iv_43.setVisibility(View.INVISIBLE);
            }else if(clickedS==18){
                iv_44.setVisibility(View.INVISIBLE);
            }else if(clickedS==19){
                iv_45.setVisibility(View.INVISIBLE);
            }

            //add points to the correct player
            playerPts++;
            tv_p1.setText("P1: "+playerPts);

        } else{
            iv_11.setImageResource((R.drawable.questionmark));
            iv_12.setImageResource((R.drawable.questionmark));
            iv_13.setImageResource((R.drawable.questionmark));
            iv_14.setImageResource((R.drawable.questionmark));
            iv_15.setImageResource((R.drawable.questionmark));
            iv_21.setImageResource((R.drawable.questionmark));
            iv_22.setImageResource((R.drawable.questionmark));
            iv_23.setImageResource((R.drawable.questionmark));
            iv_24.setImageResource((R.drawable.questionmark));
            iv_25.setImageResource((R.drawable.questionmark));
            iv_31.setImageResource((R.drawable.questionmark));
            iv_32.setImageResource((R.drawable.questionmark));
            iv_33.setImageResource((R.drawable.questionmark));
            iv_34.setImageResource((R.drawable.questionmark));
            iv_35.setImageResource((R.drawable.questionmark));
            iv_41.setImageResource((R.drawable.questionmark));
            iv_42.setImageResource((R.drawable.questionmark));
            iv_43.setImageResource((R.drawable.questionmark));
            iv_44.setImageResource((R.drawable.questionmark));
            iv_45.setImageResource((R.drawable.questionmark));

        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_15.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_25.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);
        iv_35.setEnabled(true);
        iv_41.setEnabled(true);
        iv_42.setEnabled(true);
        iv_43.setEnabled(true);
        iv_44.setEnabled(true);
        iv_45.setEnabled(true);

        //check if the game is over
        ifOver();
    }

    private void ifOver(){
        if(iv_11.getVisibility()==View.INVISIBLE &&
                iv_12.getVisibility()==View.INVISIBLE &&
                iv_13.getVisibility()==View.INVISIBLE &&
                iv_14.getVisibility()==View.INVISIBLE &&
                iv_15.getVisibility()==View.INVISIBLE &&
                iv_21.getVisibility()==View.INVISIBLE &&
                iv_22.getVisibility()==View.INVISIBLE &&
                iv_23.getVisibility()==View.INVISIBLE &&
                iv_24.getVisibility()==View.INVISIBLE &&
                iv_25.getVisibility()==View.INVISIBLE &&
                iv_31.getVisibility()==View.INVISIBLE &&
                iv_32.getVisibility()==View.INVISIBLE &&
                iv_33.getVisibility()==View.INVISIBLE &&
                iv_34.getVisibility()==View.INVISIBLE &&
                iv_35.getVisibility()==View.INVISIBLE &&
                iv_41.getVisibility()==View.INVISIBLE &&
                iv_42.getVisibility()==View.INVISIBLE &&
                iv_43.getVisibility()==View.INVISIBLE &&
                iv_44.getVisibility()==View.INVISIBLE &&
                iv_45.getVisibility()==View.INVISIBLE){

            //ifOver - End the timer
            continueT = false;

            //change the value of the score in the database or create it
            mDatabase.child(user.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user3 = dataSnapshot.getValue(User.class);
                    //if the user exist in the database and the score stored in the database is greater than the current one
                    if(dataSnapshot.exists() && user3.getPoints() > Double.parseDouble(timeDisplay)){
                        if (user != null){
                            Double score = Double.parseDouble(timeDisplay);
                            User user2 = new User(user.getDisplayName(), score);
                            mDatabase.child(user2.name).setValue(user2);
                            finalPoints = user2.getPoints();
                        }
                    }
                    //else if the data in the firebase not exist, create it
                    else if (!dataSnapshot.exists()){
                        if (user != null){
                            Double score = Double.parseDouble(timeDisplay);
                            User user2 = new User(user.getDisplayName(), score);
                            mDatabase.child(user2.name).setValue(user2);
                            finalPoints = user2.getPoints();
                            pastRecord = 999.0;
                        }
                    }

                    //get the record stored on the firebase if there is a record already
                    if (user3 != null) {
                        pastRecord = user3.getPoints();
                    }

                    //if points in firebase are greater than the current pastRecord
                    if(pastRecord > finalPoints && finalPoints != 0){
                        //record = finalPoints;
                        pastRecord = finalPoints;
                        databaseRecord = false;
                    }
                    else if (pastRecord < finalPoints){
                        pastRecord = finalPoints;
                        databaseRecord = true;
                    }
                    else {
                        databaseRecord = true;
                        finalPoints = pastRecord;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("ERROR", "Failed to connect", databaseError.toException());
                }
            });


            //wait one second in order to let the firebase update the data
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                public void run() {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mediumSingle.this);
                    //true when there is no new high score
                    if(databaseRecord == true) {
                        alertDialogBuilder
                                .setMessage("GAME OVER! \nTime elapsed: " + timeDisplay + "\n Record: " + finalPoints)
                                .setCancelable(false)
                                .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), mediumSingle.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Scoreboards", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), scoreboard.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                    else{
                        alertDialogBuilder
                                .setMessage("Congratulations! \nNew Record: " + finalPoints)
                                .setCancelable(false)
                                .setPositiveButton("PLAY AGAIN", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), mediumSingle.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Scoreboards", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getApplicationContext(), scoreboard.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            }, 1000);
        }
    }


    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(mediumSingle.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Win button for presentation purposes
    public void instaWin(View view) {
        iv_11.setVisibility(View.INVISIBLE);
        iv_12.setVisibility(View.INVISIBLE);
        iv_13.setVisibility(View.INVISIBLE);
        iv_14.setVisibility(View.INVISIBLE);
        iv_15.setVisibility(View.INVISIBLE);
        iv_21.setVisibility(View.INVISIBLE);
        iv_22.setVisibility(View.INVISIBLE);
        iv_23.setVisibility(View.INVISIBLE);
        iv_24.setVisibility(View.INVISIBLE);
        iv_25.setVisibility(View.INVISIBLE);
        iv_31.setVisibility(View.INVISIBLE);
        iv_32.setVisibility(View.INVISIBLE);
        iv_33.setVisibility(View.INVISIBLE);
        iv_34.setVisibility(View.INVISIBLE);
        iv_35.setVisibility(View.INVISIBLE);
        iv_41.setVisibility(View.INVISIBLE);
        iv_42.setVisibility(View.INVISIBLE);
        iv_43.setVisibility(View.INVISIBLE);
        iv_44.setVisibility(View.INVISIBLE);
        iv_45.setVisibility(View.INVISIBLE);

        ifOver();
    }
}
