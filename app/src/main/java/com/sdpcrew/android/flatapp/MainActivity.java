package com.sdpcrew.android.flatapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdpcrew.android.flatapp.ShoppingList.ShoppingListsActivity;
import com.sdpcrew.android.flatapp.TasksManager.TaskManagerActivity;
import com.sdpcrew.android.flatapp.Calender.CalendarMain;
import com.sdpcrew.android.flatapp.BillsManager.BillListActivity;

public class MainActivity extends AppCompatActivity {

    private Handler mControlTimer = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        mControlTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
            }
        },2000);

    }

    public void showShoppingListClick(View v) {
       startActivity(new Intent(this, ShoppingListsActivity.class));
    }

    public void showTaskManagerClick(View v) {
        startActivity(new Intent(this, TaskManagerActivity.class));
    }

    public void showCalendar (View v) {
        startActivity(new Intent(this, CalendarMain.class));
    }

    public void showBillClick (View v) {
        startActivity(new Intent(this, BillListActivity.class));
    }
}