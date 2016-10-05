package com.sdpcrew.android.flatapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private static final String STARTED = "started";
    private static boolean started = false;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            started = savedInstanceState.getBoolean(STARTED);
        }
        //This is not working at the moment
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
                    Splash.this.finish();

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
