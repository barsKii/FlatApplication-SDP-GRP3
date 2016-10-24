package com.sdpcrew.android.flatapp.Calender;

import android.os.Bundle;


import com.sdpcrew.android.flatapp.R;

import android.support.v4.app.*;



/**
 * Created by iAmacone on 1/10/16.
 *
 */

public class CalendarActivity extends FragmentActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);



        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_bills);
        if (fragment == null) {
            fragment = new CalendarFragment();
            fm.beginTransaction().add(R.id.fragment_container_bills, fragment).commit();
        }
    }
}
