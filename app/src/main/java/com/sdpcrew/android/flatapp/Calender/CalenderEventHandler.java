package com.sdpcrew.android.flatapp.Calender;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.sdpcrew.android.flatapp.R;
import com.sdpcrew.android.flatapp.ShoppingList;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by iAmacone on 17/09/16.
 */

public class CalenderEventHandler extends AppCompatActivity {

    private ArrayList<String> dayHours;;
    public GregorianCalendar hours;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_add_event);
    }



}
