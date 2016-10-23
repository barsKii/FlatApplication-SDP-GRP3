package com.sdpcrew.android.flatapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sdpcrew.android.flatapp.BillsManager.BillListActivity;
import com.sdpcrew.android.flatapp.Calender.CalendarMain;
import com.sdpcrew.android.flatapp.Database.MySqlConnection;
import com.sdpcrew.android.flatapp.ShoppingList.Item;
import com.sdpcrew.android.flatapp.ShoppingList.ShoppingList;
import com.sdpcrew.android.flatapp.ShoppingList.ShoppingListLab;
import com.sdpcrew.android.flatapp.ShoppingList.ShoppingListsActivity;
import com.sdpcrew.android.flatapp.TasksManager.TaskManagerActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MySqlConnection connection = new MySqlConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showShoppingListClick(View v) {
        startActivity(new Intent(this, ShoppingListsActivity.class));
    }

    public void showTaskManagerClick(View v) {
        startActivity(new Intent(this, TaskManagerActivity.class));
    }

    public void showCalendar(View v) {
        startActivity(new Intent(this, CalendarMain.class));
    }

    public void showBillClick(View v) {
        startActivity(new Intent(this, BillListActivity.class));
    }

    public void syncData(View v) {
        connection.execute();
//        List<ShoppingList> list = connection.readInShoppingLists();
//        // read
//        if (list != null) {
//            for (ShoppingList sl : list) {
//                if (!ShoppingListLab.get(v.getContext()).addShoppingList(sl)) {
//                    ShoppingListLab.get(v.getContext()).updateShoppingList(sl);
//                }
//            }
//        }
//
//        // write
//        connection.saveShoppingLists(ShoppingListLab.get(v.getContext()).getShoppingLists());
        Toast.makeText(v.getContext(), getString(R.string.sync_started), Toast.LENGTH_SHORT).show();
    }
}