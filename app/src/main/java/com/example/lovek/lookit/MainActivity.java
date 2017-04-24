package com.example.lovek.lookit;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;




public class MainActivity extends AppCompatActivity implements OnClickListener {

    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //declare the Mediaplayer object and assign the song to it
        sound = MediaPlayer.create(this, R.raw.omfg1);
        //Wait until the Mediaplayer is ready and play the song
        sound.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //play the song in a loop
                sound.setLooping(true);
                sound.start();
            }
        });

        //declare the sound button
        ToggleButton toggle = (ToggleButton) findViewById(R.id.music);
        toggle.callOnClick();

    }


    //If play button is pressed go First level
    public void play(View v) {
        Intent i = new Intent(MainActivity.this, easiest.class);
        startActivity(i);
        finish();
    }

    //If single player button is pressed go Single player level
    public void single(View view){
        Intent i = new Intent(MainActivity.this, mediumSingle.class);
        startActivity(i);
        finish();
    }

    //Check if the music button is pressed
    public void changeMusic(View view){
        boolean checked = ((ToggleButton)view).isChecked();
        if (checked) {
            sound.start();
        } else {
            sound.pause();
        }
    }

    //When setting image is clicked go settings activity
    public void btnSettings_onClick(View view) {
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
        finish();
    }

    //when scores image is clicked go scoreboard activity
    public void scores_onClick(View view){
        Intent intent = new Intent(this, scoreboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

    }

    //When question mark image is clicked go Info activity
    public void info(View view) {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
        finish();
    }
}
