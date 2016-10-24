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

/**
 * Displays calendar events
 * using calendar class
 */


public class CalendarMain extends AppCompatActivity {

    public Calendar mCalendar;
    public Calendar mCal;
    public CalendarAdapter mCalendarAdapter;

    public GridView mCalendarGridView;

    public Handler mHandler;
    public ArrayList<String> eventList;


    TextView mTitle;
    TextView mQoute;

    RelativeLayout mPrevious;
    RelativeLayout mNext;

    String[] separatedTime;
    String selectedGridDate;

    public String [] qoutes = {"Let go of that, which does not serve you",
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
            "i don’t regret the things I’ve done, i regret the things it i didn't do"};

    public int handler =0;

    /**
     * setOnClickListners will
     * be rewritten in the iteration
     *
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_main);

        setUpCalendar();

        /* handle calendar next,previous clicks */
        mPrevious = (RelativeLayout) findViewById(R.id.calendar_previous);

        mPrevious.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        mNext = (RelativeLayout) findViewById(R.id.calendar_next);
        mNext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();

            }
        });

        /* handle calendar grid */
        mCalendarGridView.setOnItemClickListener(new OnItemClickListener() {
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
                    /* will be change to  */
                    handler =1;


                }
                if(handler==0) {
                    ((CalendarAdapter) parent.getAdapter()).setSelected(v);
                    showToast(selectedGridDate);
                }  else{
                    showCalendarDate(v,selectedGridDate);
                }

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

        mCalendar = Calendar.getInstance(Locale.getDefault());// create calendar using current timezone
        mCal = (Calendar) mCalendar.clone(); // clone to differentiate between click changes

        eventList = new ArrayList<>();

        mCalendarAdapter = new CalendarAdapter(this, mCalendar); // send to constructor class
        mCalendarGridView = (GridView) findViewById(R.id.calendar_gridview);
        mCalendarGridView.setAdapter(mCalendarAdapter); //call to grid set up

        /* create fake events for debugging, testing , display */
        mHandler = new Handler();
        mHandler.post(calendarUpdater);

        mTitle = (TextView) findViewById(R.id.calendar_title);
        mTitle.setText(android.text.format.DateFormat.format("MMMM yyyy", mCalendar));
        mQoute= (TextView) findViewById(R.id.calendar_qoute);
        mQoute.setText(qoutes[getCalendarMonth()]);

    }

    public int getCalendarMonth(){
        return mCalendar.get(Calendar.MONTH);
    }

    protected void setNextMonth() {
        if (mCalendar.get(Calendar.MONTH) == mCalendar.getActualMaximum(Calendar.MONTH)) {
            mCalendar.set((mCalendar.get(Calendar.YEAR) + 1), mCalendar.getActualMinimum(Calendar.MONTH), 1);
        } else {
            mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) + 1);
        }

    }

    protected void setPreviousMonth() {
        if (mCalendar.get(Calendar.MONTH) == mCalendar.getActualMinimum(Calendar.MONTH)) {
            mCalendar.set((mCalendar.get(Calendar.YEAR) - 1), mCalendar.getActualMaximum(Calendar.MONTH), 1);
        } else {
            mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) - 1);
        }

    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

    }

    public void refreshCalendar() {

        mCalendarAdapter.refreshDays();
        mCalendarAdapter.notifyDataSetChanged();
        mHandler.post(calendarUpdater); // generate some mCalendar items

        mTitle.setText(android.text.format.DateFormat.format("MMMM yyyy", mCalendar));
        mQoute.setText((qoutes[mCalendar.get(Calendar.MONTH)]));
    }

    public Runnable calendarUpdater = new Runnable() {

        @Override
        public void run() {
            eventList.clear();

            // Print dates of the current week
            for (int i = 0; i < 7; i++) {
                mCal.add(Calendar.DATE, 1);
                eventList.add("2012-09-12");
                eventList.add("2012-10-07");
                eventList.add("2012-10-15");
                eventList.add("2012-10-20");
                eventList.add("2012-11-30");
                eventList.add("2012-11-28");
            }

            mCalendarAdapter.setItems(eventList);
            mCalendarAdapter.notifyDataSetChanged();
        }
    };

    public void addCalendarEvents (View v) {
        startActivity(new Intent(this, CalendarActivity.class));
    }

    public void showCalendarDate (View v,String dateSelected) {

        Intent calendarDateSelected = new Intent(this, CalenderEventHandler.class);
        calendarDateSelected.putExtra("DateSelected",dateSelected);
        startActivity(calendarDateSelected);
    }
}