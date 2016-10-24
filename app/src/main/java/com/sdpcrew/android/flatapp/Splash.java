package com.sdpcrew.android.flatapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.sdpcrew.android.flatapp.Database.BaseHelper;

/**
 * The Splash class is responsible for the welcome screen (splashScreen) for the app.
 * It will show a picture while the app loads. We also plan to implement a loading bar in the future.
 */
public class Splash extends Activity {

    public static SQLiteDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = new BaseHelper(getApplicationContext())
                .getWritableDatabase();

        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
        Splash.this.startActivity(mainIntent);
        Splash.this.finish();

    }
}
