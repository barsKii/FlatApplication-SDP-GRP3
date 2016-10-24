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
        new ReadData().execute();
        new WriteData().execute(ShoppingListLab.get(v.getContext()).getShoppingLists());
        connection.closeConnections();
        Toast.makeText(v.getContext(), getString(R.string.sync_started), Toast.LENGTH_SHORT).show();
    }

    private class ReadData extends AsyncTask<Void, Void, List<ShoppingList>> {

        @Override
        protected List<ShoppingList> doInBackground(Void... params) {
            return connection.readInShoppingLists();
        }

        protected void onPostExecute(List<ShoppingList> list) {
            if (list != null) {
                boolean exists = false;
                List<ShoppingList> shopping = ShoppingListLab.get(getApplicationContext()).getShoppingLists();
                for (ShoppingList sl : list) {
                    for (ShoppingList s: shopping) {
                        if(sl.getListName().equals(s.getListName())) {
                            ShoppingListLab.get(getApplicationContext()).removeShoppingList(s);
                            ShoppingListLab.get(getApplicationContext()).addShoppingList(sl);
                            exists = true;
                            break;
                        }
                    }
                    if(!exists) {
                        ShoppingListLab.get(getApplicationContext()).addShoppingList(sl);
                    } else exists = false;
                }
            }
        }
    }

    private class WriteData extends AsyncTask<List<ShoppingList>, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<ShoppingList>... params) {
            if (params[0].size() != 0) {
                connection.saveShoppingLists(params[0]);
            }
            return null;
        }
    }
}