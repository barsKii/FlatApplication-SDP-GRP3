package com.sdpcrew.android.flatapp.BillsManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sdpcrew.android.flatapp.*;
/**
 * Created by David on 21/09/2016.
 * An abstract class used to reduce the code re-use of each fragment class
 */
public abstract class SingleFragmentActivityBill extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_bills);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_bills, fragment)
                    .commit();
        }
    }
}
