package com.sdpcrew.android.flatapp.Calender;

import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import com.sdpcrew.android.flatapp.*;
import android.content.Context;
import java.util.*;
import java.text.*;
import android.graphics.Color;

/**
 * Created by iAmacone on 11/09/16.
 */

public class CalendarAdapter extends BaseAdapter {

    private Context mContext;

    public static List<String> dayEvents;
    private Calendar calendarRef;
    public GregorianCalendar prevMonth;
    public GregorianCalendar Monthg;
    private GregorianCalendar CalendarClone;

    private View previousView;

    private String curentDate;
    private DateFormat df;

    private ArrayList<String> items;


    int firstDayOfMonth;
    int MaxWeekInMonth;
    int MonthLength;
    int DaysInAMonth;
    int offDays;

    String itemvalue;

    public CalendarAdapter(Context c, GregorianCalendar CalendarDetails) {
        /* inti variables */
        dayEvents = new ArrayList();
        calendarRef = CalendarDetails;
        CalendarClone = (GregorianCalendar) CalendarDetails.clone();
        this.items = new ArrayList<String>();
        mContext = c;

        calendarRef.set(Calendar.DAY_OF_MONTH,1);
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        curentDate = df.format(CalendarClone.getTime());
        refreshDays();
    }



    public void refreshDays() {
        items.clear();
        dayEvents.clear();// keep an eye on varibale for future
        Locale.setDefault(Locale.US);
        prevMonth = (GregorianCalendar) calendarRef.clone();// clone again for proctection
        firstDayOfMonth = getFirstDayOfMonth();
        MaxWeekInMonth = getMaxWeekInMonth();
        MonthLength = MaxWeekInMonth * 7;// help make the grid
        DaysInAMonth = getMaxDaysInAMonth();
        offDays = DaysInAMonth - (firstDayOfMonth - 1);
        Monthg = (GregorianCalendar) prevMonth.clone();
        Monthg.set(GregorianCalendar.DAY_OF_MONTH, offDays + 1);

        //Build Grid
        for (int n = 0; n < MonthLength; n++) {

            itemvalue = df.format(Monthg.getTime());
            Monthg.add(GregorianCalendar.DATE, 1);
            dayEvents.add(itemvalue);

        }
    }

    private int getMaxDaysInAMonth() {
        int maxDays;
        if (calendarRef.get(GregorianCalendar.MONTH) == calendarRef.getActualMinimum(GregorianCalendar.MONTH)) {
            calendarRef.set((calendarRef.get(GregorianCalendar.YEAR) - 1), calendarRef.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            calendarRef.set(GregorianCalendar.MONTH, calendarRef.get(GregorianCalendar.MONTH) - 1);
        }
        maxDays = calendarRef.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        return maxDays;
    }

    public void setItems(ArrayList<String> items) {
        for (int i = 0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
    }

    @Override
    public int getCount() {
        return dayEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return dayEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_tile, null);
        }
        dayView = (TextView) v.findViewById(R.id.date);
        String[] separatedTime = dayEvents.get(position).split("-");
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDayOfMonth)) {
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.WHITE);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            dayView.setTextColor(Color.BLUE);
        }
        if (dayEvents.get(position).equals(curentDate)) {
            setSelected(v);
            previousView = v;
        } else {
            v.setBackgroundResource(R.drawable.list_item_background);
        }
        dayView.setText(gridvalue);

        String date = dayEvents.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (calendarRef.get(GregorianCalendar.MONTH) + 1);
        if (monthStr.length() == 1) {
            monthStr = "0" + monthStr;
        }

        ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
        if (date.length() > 0 && items != null && items.contains(date)) {
            iw.setVisibility(View.VISIBLE);
        } else {
            iw.setVisibility(View.INVISIBLE);
        }
        return v;

    }

    public View setSelected(View view) {
        if (previousView != null) {
            previousView.setBackgroundResource(R.drawable.list_item_background);
        }
        previousView = view;
        view.setBackgroundResource(R.drawable.calendar_cell);
        return view;
    }

    public int getFirstDayOfMonth(){
        return calendarRef.get(GregorianCalendar.DAY_OF_WEEK);
    }

    public int getMaxWeekInMonth(){
        return calendarRef.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
    }


}


