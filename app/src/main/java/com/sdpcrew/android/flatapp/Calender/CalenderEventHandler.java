package com.sdpcrew.android.flatapp.Calender;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.sdpcrew.android.flatapp.R;
import java.util.*;


/**
 * Created by iAmacone on 17/09/16.
 * class to be implemented in the next iteration
 * calender listens to CalendarEventHandler to
 * add event
 */

public class CalenderEventHandler extends FragmentActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_day_list);

        Bundle getDateSelected =getIntent().getExtras();
        String dateSelect = getDateSelected.getString("DateSelected");

        TextView dateSet = (TextView) findViewById(R.id.calendar_date_selected);
        dateSet.setText(dateSelect);
    }
}
