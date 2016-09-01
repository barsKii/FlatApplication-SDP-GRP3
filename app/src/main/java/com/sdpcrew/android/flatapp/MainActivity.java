package com.sdpcrew.android.flatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mGroceryButton;
    private Button mCalendarButton;
    private Button mTasksButton;
    private Button mBillsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // All buttons initialized to the linked button, no listener as of yet. Coolies
        mGroceryButton = (Button) findViewById(R.id.grocery_button);

        mCalendarButton = (Button) findViewById(R.id.calendar_button);

        mTasksButton = (Button) findViewById(R.id.tasks_button);

        mBillsButton = (Button) findViewById(R.id.bills_button);
    }
}
