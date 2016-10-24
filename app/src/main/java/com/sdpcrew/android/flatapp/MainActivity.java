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

    /**
     * onClick method to show the shopping list screen.
     */
    public void showShoppingListClick(View v) {
        startActivity(new Intent(this, ShoppingListsActivity.class));
    }

    /**
     * onClick method to show the task manager screen.
     */
    public void showTaskManagerClick(View v) {
        startActivity(new Intent(this, TaskManagerActivity.class));
    }

    /**
     * onClick method to show the calendar screen.
     */
    public void showCalendar(View v) {
        startActivity(new Intent(this, CalendarMain.class));
    }

    /**
     * onClick method to show the bills screen.
     */
    public void showBillClick(View v) {
        startActivity(new Intent(this, BillListActivity.class));
    }

    /**
     * This function is the onClick method for the sync button located on the main menu. When clicked
     * a connection to the online server is made where data is first read, then written. Essentially
     * this syncs the users app to the most current data. The database connection is closed afterwards.
     * A notification (toast) is also shown to the user to tell them the sync has started.
     */
    public void syncData(View v) {
        new ReadData().execute();
        new WriteData().execute(ShoppingListLab.get(v.getContext()).getShoppingLists());
        connection.closeConnections();
        Toast.makeText(v.getContext(), getString(R.string.sync_started), Toast.LENGTH_SHORT).show();
    }


    /**
     * This class is a AsyncTask which is used to connect to FlatApp's online server and read
     * data to be added to the users app by using SQL statements.
     */
    private class ReadData extends AsyncTask<Void, Void, List<ShoppingList>> {

        @Override
        protected List<ShoppingList> doInBackground(Void... params) {
            return connection.readInShoppingLists(); // Read in data
        }

        protected void onPostExecute(List<ShoppingList> list) { // Pass in data to be used
            if (list != null) {
                boolean exists = false;
                List<ShoppingList> shopping = ShoppingListLab.get(getApplicationContext()).getShoppingLists();
                for (ShoppingList sl : list) { // for every new list
                    for (ShoppingList s : shopping) { // for every existing list
                        if (sl.getListName().equals(s.getListName())) { // if user already has the list
                            ShoppingListLab.get(getApplicationContext()).removeShoppingList(s); // remove the list
                            ShoppingListLab.get(getApplicationContext()).addShoppingList(sl); // add the new one
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) { // user did not have the list, so add it to their app.
                        ShoppingListLab.get(getApplicationContext()).addShoppingList(sl);
                    } else exists = false;
                }
            }
        }
    }

    /**
     * This class is a AsyncTask which is used to connect to FlatApp's online server and write
     * data to it using SQL statements.
     */
    private class WriteData extends AsyncTask<List<ShoppingList>, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<ShoppingList>... params) {
            if (params[0].size() != 0) {
                connection.saveShoppingLists(params[0]); // write data to server
            }
            return null;
        }
    }
}