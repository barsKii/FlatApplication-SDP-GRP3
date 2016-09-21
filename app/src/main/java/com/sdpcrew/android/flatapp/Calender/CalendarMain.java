package com.sdpcrew.android.flatapp.Calender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import com.sdpcrew.android.flatapp.*;

import android.content.Intent;
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

    public Calendar calendar;
    public Calendar cal;
    public CalendarAdapter calendarAdapter;

    public GridView calendarGridView;

    public Handler handler;
    public ArrayList<String> eventList;


    TextView title;
    TextView qoute;

    RelativeLayout previous;
    RelativeLayout next;

    String[] separatedTime;
    String selectedGridDate;

    public String [] qoutes = {"Let go of that ,which does not serve you",
            "It does not matter how slowly you go as long as you do not stop",
            "You have to learn the rules of the game and then have to play better than anyone else",
            "Problems are not signs they are guidelines",
            "Don’t watch the clock; do what it does. Keep going",
            "Expect problems and eat them for breakfast",
            "The best revenge is massive success",
            "Don’t be afraid to stand for what you believe in even if that means standing alone",
            "In order to succeed your desire for success should be greater then fear of failure",
            "Accepting responsibility for your life ",
            "Challenges are what makes life interesting and overcoming them makes life meaningful",
            "i don’t regret the things I’ve done, i regret the things it i didnt do"};



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_main);

        setUpCalendar();

        previous = (RelativeLayout) findViewById(R.id.calendar_previous);

        previous.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        next = (RelativeLayout) findViewById(R.id.calendar_next);
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
                selectedGridDate = CalendarAdapter.dayEvents.get(position);
                separatedTime = selectedGridDate.split("-");
                String gridvalueString = separatedTime[2].replaceFirst("^0*", "");
                int gridvalue = Integer.parseInt(gridvalueString);
                if ((gridvalue > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                } else if ((gridvalue < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }else{
                    setContentView(R.layout.calendar_day_list);
                    TextView date = (TextView)  findViewById(R.id.calendar_test_string);
                    date.setText(separatedTime[0]+"/"+separatedTime[1]+"/"+separatedTime[2]);
                }
                ((CalendarAdapter) parent.getAdapter()).setSelected(v);
                showToast(selectedGridDate);

            }
        });
    }

    /**
     * qiuck reference to calender
     * E.G GUIDE
     * MMMM - January
     * MM - JAN
     * M - 1
     */


    public void setUpCalendar() {


        calendar = calendar.getInstance(Locale.getDefault());
        cal = (Calendar) calendar.clone();

        eventList = new ArrayList<String>();

        calendarAdapter = new CalendarAdapter(this, calendar);
        calendarGridView = (GridView) findViewById(R.id.calendar_gridview);
        calendarGridView.setAdapter(calendarAdapter);

        handler = new Handler();
        handler.post(calendarUpdater);

        title = (TextView) findViewById(R.id.calendar_title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", calendar));
        qoute= (TextView) findViewById(R.id.calendar_qoute);
        qoute.setText(qoutes[getCalendarMonth()]);

    }
    public int getCalendarMonth(){
        return calendar.get(Calendar.MONTH);
    }

    protected void setNextMonth() {
        if (calendar.get(Calendar.MONTH) == calendar.getActualMaximum(Calendar.MONTH)) {
            calendar.set((calendar.get(Calendar.YEAR) + 1), calendar.getActualMinimum(Calendar.MONTH), 1);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (calendar.get(Calendar.MONTH) == calendar.getActualMinimum(Calendar.MONTH)) {
            calendar.set((calendar.get(Calendar.YEAR) - 1), calendar.getActualMaximum(Calendar.MONTH), 1);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        }

    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar() {


        calendarAdapter.refreshDays();
        calendarAdapter.notifyDataSetChanged();
        handler.post(calendarUpdater); // generate some calendar items

        title.setText(android.text.format.DateFormat.format("MMMM yyyy", calendar));
        qoute.setText((qoutes[calendar.get(Calendar.MONTH)]));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            eventList.clear();

            // Print dates of the current week
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            String itemvalue;
            for (int i = 0; i < 7; i++) {
                itemvalue = df.format(cal.getTime());
                cal.add(Calendar.DATE, 1);
                eventList.add("2012-09-12");
                eventList.add("2012-10-07");
                eventList.add("2012-10-15");
                eventList.add("2012-10-20");
                eventList.add("2012-11-30");
                eventList.add("2012-11-28");
            }

            calendarAdapter.setItems(eventList);
            calendarAdapter.notifyDataSetChanged();
        }
    };

    public void addCalendarEvents (View v) {
        startActivity(new Intent(this, CalenderEventHandler.class));
    }
}