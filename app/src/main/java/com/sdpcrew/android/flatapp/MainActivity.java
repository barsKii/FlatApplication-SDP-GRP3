package com.sdpcrew.android.flatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        // LEAVE GROCERY AS IS. DON'T ADD LISTENER.
        mGroceryButton = (Button) findViewById(R.id.shopping_list_button);

        mCalendarButton = (Button) findViewById(R.id.calendar_button);
        mTasksButton = (Button) findViewById(R.id.tasks_button);
        mBillsButton = (Button) findViewById(R.id.bills_button);





    }

    // Shane's code starts here. Please don't touch :)
    public void showShoppingListClick(View v) {
       startActivity(new Intent(this, ShoppingListsActivity.class));
    }
    // Shane's code ends here.
}