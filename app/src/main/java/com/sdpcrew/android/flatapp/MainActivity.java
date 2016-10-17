package com.sdpcrew.android.flatapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdpcrew.android.flatapp.ShoppingList.ShoppingListsActivity;
import com.sdpcrew.android.flatapp.TasksManager.TaskManagerActivity;
import com.sdpcrew.android.flatapp.Calender.CalendarMain;
import com.sdpcrew.android.flatapp.BillsManager.BillListActivity;
import com.sdpcrew.android.flatapp.database.BaseHelper;

public class MainActivity extends AppCompatActivity {

    public static SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = new BaseHelper(getBaseContext())
                .getWritableDatabase();
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