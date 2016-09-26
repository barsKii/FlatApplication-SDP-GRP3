package com.sdpcrew.android.flatapp;
import android.content.Context;

import com.sdpcrew.android.flatapp.Calender.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by iAmacone on 1/09/2016.
 * Unit Test Class for CalendarMain Class.
 */

public class CalendarMainUnitTest {

    private Context mContext;
    private Calendar calendar;
    private Calendar clone;
    CalendarAdapter calendarAdapter;

    @Before
    public void setUp(){
        calendar = Calendar.getInstance(Locale.getDefault());
        clone = (Calendar) calendar.clone();
        calendarAdapter = new CalendarAdapter(mContext,calendar);
    }
    @Test
    public void testgetMaxDaysInAMonth(){
        int max= calendarAdapter.getMaxDaysInAMonth()-1;
        Assert.assertEquals(30,max);
    }
    @Test
    public void getFirstDayOfMonth(){
        int first = calendarAdapter.getFirstDayOfMonth();
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_WEEK),first);
    }
    @Test
    public void getMaxWeekInMonth(){
        int max = calendarAdapter.getMaxWeekInMonth();
        Assert.assertEquals(calendar.getActualMaximum(Calendar.WEEK_OF_MONTH),max);
    }
}
