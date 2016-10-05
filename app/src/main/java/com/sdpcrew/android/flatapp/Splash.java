package com.sdpcrew.android.flatapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * This class is responsible for the welcome screen ( splashScreen) or the initilization screen
 * It will show a picture while the app loads. We plan to implement a loading bar too
 */
public class Splash extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000; //Sets time splash screen will be active
    private static final String STARTED = "started"; // for saving purpose when app is destroyed
    private boolean started = false; //holds info whether this screen was shown or not already

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retrieves value store in STARTED constant and updates started variable
        if (savedInstanceState != null) {
            started = savedInstanceState.getBoolean(STARTED);
        }
        //This is not working the way I intended at the moment
        //If app has been initialzed creates an intent of main class and ends this activity.
        // Otherwise, create an intent and only finish it when the SPLASH_DISPLAY_LENGTH ends.
        if(started){
            Intent mainIntent = new Intent(Splash.this,MainActivity.class);
            Splash.this.startActivity(mainIntent);
            Splash.this.finish();

        }else{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();//finishes this activity

                }
            }, SPLASH_DISPLAY_LENGTH);
        }


    }
    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
        onSavedInstanceState.putBoolean(STARTED,started);
    }
}
