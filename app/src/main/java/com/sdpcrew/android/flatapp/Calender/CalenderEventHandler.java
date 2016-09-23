package com.sdpcrew.android.flatapp.Calender;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.sdpcrew.android.flatapp.R;
import java.util.*;


/**
 * Created by iAmacone on 17/09/16.
 * class to be implemented in the next iteration
 * calender listens to CalendarEventHandler to
 * add event
 */

public class CalenderEventHandler extends AppCompatActivity {

    private ArrayList<String> dayHours;
    public Calendar hours;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_add_event);
    }
}
