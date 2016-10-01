package com.sdpcrew.android.flatapp.Calender;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sdpcrew.android.flatapp.R;

/**
 * Created by iAmacone on 30/09/16.
 * Thanks david for the setup
 */

public abstract class SingleFragmentActivityCalendar extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);// might need to change

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_bills);// calls from activity fragment
        //may cuase calendar to crush
        //might need to change at a later stage.

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container_bills, fragment).commit();// read comment above
        }
    }
}
