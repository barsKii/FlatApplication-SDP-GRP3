package com.sdpcrew.android.flatapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.sdpcrew.android.flatapp.TasksManager.TaskManagerActivity;

import com.sdpcrew.android.flatapp.Calender.CalendarMain;

public class MainActivity extends AppCompatActivity {

    private ImageButton mCalendarButton;
    private ImageButton mTasksButton;
    private ImageButton mBillsButton;

    private Handler mControlTimer = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        // All buttons initialized to the linked button, no listener as of yet. Coolies
        mCalendarButton = (ImageButton) findViewById(R.id.calendar_button);
        mTasksButton = (ImageButton) findViewById(R.id.tasks_button);
        mBillsButton = (ImageButton) findViewById(R.id.bills_button);

        mControlTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
            }
        },3000);

    }


    // Shane's code starts here. Please don't touch :)
    public void showShoppingListClick(View v) {
       startActivity(new Intent(this, ShoppingListsActivity.class));
    }
    // Shane's code ends here.

    public void showTaskManagerClick(View v) {
        startActivity(new Intent(this, TaskManagerActivity.class));
    }
    public void showCalendar (View v) {
        startActivity(new Intent(this, CalendarMain.class));
    }
}