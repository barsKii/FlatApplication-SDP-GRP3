package com.sdpcrew.android.flatapp.Calender;

import java.util.*;


/**
 * Created by iAmacone on 1/10/16.
 *
 */

class CalendarCon {

    private UUID mCalendarId;
    private String mCalendarTitle;
    private Date mCalendarDateFrom;

    CalendarCon() {
        mCalendarId = UUID.randomUUID();
        mCalendarDateFrom = new Date();

    }

    public UUID getId() {
        return mCalendarId;
    }

    public String getTitle() {
        return mCalendarTitle;
    }

    public void setTitle(String title) {
        mCalendarTitle = title;
    }

    @Override
    public String toString() {
        return mCalendarTitle + "\nmAllDay: " +  "\n" + mCalendarDateFrom + "\n" +  "\n";
    }

}
