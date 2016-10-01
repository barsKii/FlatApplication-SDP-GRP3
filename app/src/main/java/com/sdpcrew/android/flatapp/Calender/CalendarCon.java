package com.sdpcrew.android.flatapp.Calender;

import java.util.*;
import java.util.Date;


/**
 * Created by iAmacone on 1/10/16.
 */

public class CalendarCon {

    // contructors for setting up a calendar event
    // may need to implement db/json attributes


    private UUID mCalendarId;
    private String mCalendarTitle;
    private boolean mAllDay;
    private Date mCalendarDateTo;
    private Date mCalendarDateFrom;
    private String mCalendarDesc;

    public CalendarCon(){
        mCalendarId= UUID.randomUUID();
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

    public boolean isAllDay() {
        return mAllDay;
    }

    public void setIsAllDay(boolean allDay) {
        mAllDay = allDay;
    }


    public Date getDateFrom() {
        return mCalendarDateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        mCalendarDateFrom = dateFrom;
    }

    public Date getDateTo() {
        return mCalendarDateTo;
    }

    public void setDateTo(Date dateTo) {
        mCalendarDateTo = dateTo;
    }

    public String getDesc() {
        return mCalendarDesc;
    }

    public void setDesc(String Desc) {
        mCalendarDesc = Desc;
    }

    @Override
    public String toString() {
        return mCalendarTitle + "\nmAllDay: " + mAllDay +"\n"+mCalendarDateFrom + "\n"+ mCalendarDateTo + "\n" + mCalendarDesc  ;
    }

}
