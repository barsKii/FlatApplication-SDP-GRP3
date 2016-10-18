package com.sdpcrew.android.flatapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.sdpcrew.android.flatapp.database.BaseHelper;

/**
 * The Splash class is responsible for the welcome screen (splashScreen) for the app.
 * It will show a picture while the app loads. We also plan to implement a loading bar in the future.
 */
public class Splash extends Activity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // Duration that the splash screen will be active
    private static final String STARTED = "started"; // for saving purpose when app is destroyed
    private boolean screenShown; //Whether or not the screen has been shown
    public static SQLiteDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = new BaseHelper(getBaseContext())
                .getWritableDatabase();

        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
        Splash.this.startActivity(mainIntent);
        Splash.this.finish();

        // Updates screenShown based on the current state.
        if (savedInstanceState != null) {
            screenShown = savedInstanceState.getBoolean(STARTED);
        }
        // This is not working as intended at the moment.
        // If the app has been initialised it creates an intent of the main class and ends this activity.
        // Otherwise, it create an intent and only finishes when the SPLASH_DISPLAY_LENGTH ends.
//        if (screenShown) {
//            Intent mainIntent = new Intent(Splash.this, MainActivity.class);
//            Splash.this.startActivity(mainIntent);
//            Splash.this.finish();
//        } else {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
//                    Splash.this.startActivity(mainIntent);
//                    Splash.this.finish(); //finishes this activity.
//                }
//            }, SPLASH_DISPLAY_LENGTH);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
        onSavedInstanceState.putBoolean(STARTED, screenShown);
    }
}
