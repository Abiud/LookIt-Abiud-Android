package com.example.lovek.lookit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

import static com.example.lovek.lookit.R.layout.easy_levels;

public class easy_levels extends AppCompatActivity {

    TextView tv_p1, tv_p2;
    ImageView iv_11,iv_12,iv_13,iv_14,iv_21,iv_22,iv_23,iv_24,iv_31,iv_32,iv_33,iv_34;

    //array for the images
    Integer[] cardAr={101,102,103,104,105,106,201,202,203,204,205,206};

    //actual images
    int image101, image102, image103, image104, image105, image106, image201, image202,image203,image204,image205,image206;

    int firstC, secC;
    int clickedF, clickedS;
    int cardNum=1;

    int turn=1;
    int playerPts=0, cpuPts=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.easy_levels);

        tv_p1=(TextView) findViewById(R.id.tv_p1);
        tv_p2=(TextView) findViewById(R.id.tv_p2);

        iv_11=(ImageView) findViewById(R.id.iv_11);
        iv_12=(ImageView) findViewById(R.id.iv_12);
        iv_13=(ImageView) findViewById(R.id.iv_13);
        iv_14=(ImageView) findViewById(R.id.iv_14);
        iv_21=(ImageView) findViewById(R.id.iv_21);
        iv_22=(ImageView) findViewById(R.id.iv_22);
        iv_23=(ImageView) findViewById(R.id.iv_23);
        iv_24=(ImageView) findViewById(R.id.iv_24);
        iv_31=(ImageView) findViewById(R.id.iv_31);
        iv_32=(ImageView) findViewById(R.id.iv_32);
        iv_33=(ImageView) findViewById(R.id.iv_33);
        iv_34=(ImageView) findViewById(R.id.iv_34);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        //load the card images
        frontOfCardsResources();

        //shuffle the images
        Collections.shuffle(Arrays.asList(cardAr));

        //changing the color of the second player
        tv_p1.setTextColor(Color.WHITE);
        tv_p2.setTextColor(Color.BLACK);

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
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

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
        image201=R.drawable.image201;
        image202=R.drawable.image202;
        image203=R.drawable.image203;
        image204=R.drawable.image204;
        image205=R.drawable.image205;
        image206=R.drawable.image206;
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
                iv_21.setVisibility(View.INVISIBLE);
            }else if(clickedF==5){
                iv_22.setVisibility(View.INVISIBLE);
            }else if(clickedF==6){
                iv_23.setVisibility(View.INVISIBLE);
            }else if(clickedF==7){
                iv_24.setVisibility(View.INVISIBLE);
            }else if(clickedF==8){
                iv_31.setVisibility(View.INVISIBLE);
            }else if(clickedF==9){
                iv_32.setVisibility(View.INVISIBLE);
            }else if(clickedF==10){
                iv_33.setVisibility(View.INVISIBLE);
            }else if(clickedF==11){
                iv_34.setVisibility(View.INVISIBLE);
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
                iv_21.setVisibility(View.INVISIBLE);
            }else if(clickedS==5){
                iv_22.setVisibility(View.INVISIBLE);
            }else if(clickedS==6){
                iv_23.setVisibility(View.INVISIBLE);
            }else if(clickedS==7){
                iv_24.setVisibility(View.INVISIBLE);
            }else if(clickedS==8){
                iv_31.setVisibility(View.INVISIBLE);
            }else if(clickedS==9){
                iv_32.setVisibility(View.INVISIBLE);
            }else if(clickedS==10){
                iv_33.setVisibility(View.INVISIBLE);
            }else if(clickedS==11){
                iv_34.setVisibility(View.INVISIBLE);
            }

            //add points to the correct player
            if(turn==1){
                playerPts++;
                tv_p1.setText("P1: "+playerPts);
            } else if(turn==2){
                cpuPts++;
                tv_p2.setText("P2: "+cpuPts);
            }
        } else{
            iv_11.setImageResource((R.drawable.questionmark));
            iv_12.setImageResource((R.drawable.questionmark));
            iv_13.setImageResource((R.drawable.questionmark));
            iv_14.setImageResource((R.drawable.questionmark));
            iv_21.setImageResource((R.drawable.questionmark));
            iv_22.setImageResource((R.drawable.questionmark));
            iv_23.setImageResource((R.drawable.questionmark));
            iv_24.setImageResource((R.drawable.questionmark));
            iv_31.setImageResource((R.drawable.questionmark));
            iv_32.setImageResource((R.drawable.questionmark));
            iv_33.setImageResource((R.drawable.questionmark));
            iv_34.setImageResource((R.drawable.questionmark));

            //change players turn
            if(turn ==1){
                turn=2;
                tv_p1.setTextColor(Color.BLACK);
                tv_p2.setTextColor(Color.WHITE);
            } else if(turn ==2){
                turn=1;
                tv_p1.setTextColor(Color.WHITE);
                tv_p2.setTextColor(Color.BLACK);
            }
        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        //check if the game is over
        ifOver();
    }

    private void ifOver(){
        if(iv_11.getVisibility()==View.INVISIBLE &&
                iv_12.getVisibility()==View.INVISIBLE &&
                iv_13.getVisibility()==View.INVISIBLE &&
                iv_14.getVisibility()==View.INVISIBLE &&
                iv_21.getVisibility()==View.INVISIBLE &&
                iv_22.getVisibility()==View.INVISIBLE &&
                iv_23.getVisibility()==View.INVISIBLE &&
                iv_24.getVisibility()==View.INVISIBLE &&
                iv_31.getVisibility()==View.INVISIBLE &&
                iv_32.getVisibility()==View.INVISIBLE &&
                iv_33.getVisibility()==View.INVISIBLE &&
                iv_34.getVisibility()==View.INVISIBLE){
            String winner;
            if(playerPts > cpuPts)
            {
                winner= "P1";
            }
            else if(playerPts == cpuPts){
                winner = "Tie!";
            }
            else {
                winner="P2";
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(easy_levels.this);
            alertDialogBuilder
                    .setMessage("GAME OVER! \nP1: "+playerPts+"\nP2: "+ cpuPts+"\nWINNER: "+winner)
                    .setCancelable(false)
                    .setPositiveButton("NEXT LEVEL", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent= new Intent(easy_levels.this, medium.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                            finish();
                        }
                    });
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(easy_levels.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
