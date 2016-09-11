package com.sdpcrew.android.flatapp.Calender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import com.sdpcrew.android.flatapp.*;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarMain extends AppCompatActivity {

    public GregorianCalendar calendar;
    public CalendarAdapter calendarAdapter;

    public GridView calendarGridView;

    public ArrayList<String> eventList;
    public Handler handler;
    public GregorianCalendar itemmonth;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_main);
        eventList = new ArrayList<String>();
        setUpCalendar();

        RelativeLayout previous = (RelativeLayout) findViewById(R.id.calendar_previous);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        RelativeLayout next = (RelativeLayout) findViewById(R.id.calendar_next);
        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        calendarGridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ((CalendarAdapter) parent.getAdapter()).setSelected(v);
                String selectedGridDate = CalendarAdapter.dayEvents.get(position);
                String[] separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*", "");
                int gridvalue = Integer.parseInt(gridvalueString);
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v);
                showToast(selectedGridDate);
            }
        });
    }

    public void setUpCalendar() {
        calendar = (GregorianCalendar) GregorianCalendar.getInstance(Locale.getDefault());
        itemmonth = (GregorianCalendar) calendar.clone();
        calendarAdapter = new CalendarAdapter(this,calendar);
        calendarGridView = (GridView) findViewById(R.id.calendar_gridview);
        calendarGridView.setAdapter(calendarAdapter);
        handler = new Handler();
        handler.post(this.calendarUpdater);
        ((TextView) findViewById(R.id.calendar_title)).setText(android.text.format.DateFormat.format("MMMM yyyy", calendar));
    }

    protected void setNextMonth() {
        if (calendar.get(GregorianCalendar.MONTH) == calendar.getActualMaximum(GregorianCalendar.MONTH)) {
            calendar.set((calendar.get(GregorianCalendar.YEAR) + 1),
                    calendar.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            calendar.set(GregorianCalendar.MONTH,
                    calendar.get(GregorianCalendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (calendar.get(GregorianCalendar.MONTH) == calendar.getActualMinimum(GregorianCalendar.MONTH)) {
            calendar.set((calendar.get(GregorianCalendar.YEAR) - 1),
                    calendar.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            calendar.set(GregorianCalendar.MONTH,
                    calendar.get(GregorianCalendar.MONTH) - 1);
        }

    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar() {
        TextView title = (TextView) findViewById(R.id.calendar_title);

        calendarAdapter.refreshDays();
        calendarAdapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", calendar));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            eventList.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            String itemvalue;
            for (int i = 0; i < 7; i++) {
                itemvalue = df.format(itemmonth.getTime());
                itemmonth.add(GregorianCalendar.DATE, 1);
                eventList.add("2016-09-12");
                eventList.add("2016-10-07");
                eventList.add("2016-10-15");
                eventList.add("2016-10-20");
                eventList.add("2016-11-30");
                eventList.add("2016-11-28");
            }

            calendarAdapter.setItems(eventList);
            calendarAdapter.notifyDataSetChanged();
        }
    };
}
