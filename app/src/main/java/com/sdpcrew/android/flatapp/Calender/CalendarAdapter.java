package com.sdpcrew.android.flatapp.Calender;

import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import com.sdpcrew.android.flatapp.*;
import android.content.Context;

import java.io.Console;
import java.util.*;
import java.text.*;
import android.graphics.Color;
import android.util.*;
import android.widget.Toast;


/**
 * Created by iAmacone on 11/09/16.
 */

public class CalendarAdapter extends BaseAdapter {

    private Context mContext;

    public static List<String> dayEvents;
    private Calendar calendarRef;
    public Calendar prevMonth;
    public Calendar Monthg;
    private Calendar CalendarClone;

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
    private static final String TAG = "Calender";

    public CalendarAdapter(Context c, Calendar CalendarDetails) {
        /* inti variables */
        CalendarAdapter.dayEvents = new ArrayList();
        calendarRef = CalendarDetails;
        CalendarClone = (Calendar) CalendarDetails.clone();
        mContext = c;
        calendarRef.set(Calendar.DAY_OF_MONTH,1);

        this.items = new ArrayList<String>();

        df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        curentDate = df.format(CalendarClone.getTime());

        refreshDays();

    }



    public void refreshDays() {
        items.clear();
        dayEvents.clear();// keep an eye on varibale for future
        prevMonth = (Calendar) calendarRef.clone();// clone again for proctection
        firstDayOfMonth = getFirstDayOfMonth();
        MaxWeekInMonth = getMaxWeekInMonth();
        MonthLength = MaxWeekInMonth * 7;// help make the grid
        DaysInAMonth = getMaxDaysInAMonth();
        offDays = DaysInAMonth - (firstDayOfMonth - 1);
        Monthg = (Calendar) prevMonth.clone();
        Monthg.set(Calendar.DAY_OF_MONTH, offDays + 1);

        //Build Grid
        for (int n = 0; n < MonthLength; n++) {

            itemvalue = df.format(Monthg.getTime());
            Monthg.add(Calendar.DATE, 1);
            dayEvents.add(itemvalue);

        }
    }

    private int getMaxDaysInAMonth() {
        int maxDays;
        if (calendarRef.get(Calendar.MONTH) == calendarRef.getActualMinimum(Calendar.MONTH)) {
            prevMonth.set((calendarRef.get(Calendar.YEAR) - 1), calendarRef.getActualMaximum(Calendar.MONTH), 1);
        } else {
            prevMonth.set(Calendar.MONTH, calendarRef.get(Calendar.MONTH) - 1);
        }
        maxDays = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
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
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.calendar_tile, null);
        }
        dayView = (TextView) v.findViewById(R.id.date);
        String[] separatedTime = dayEvents.get(position).split("-");
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");

        if ((Integer.parseInt(gridvalue) > 1) && (position < firstDayOfMonth)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        } else {
            dayView.setTextColor(Color.WHITE);
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
        String monthStr = "" + (calendarRef.get(Calendar.MONTH) + 1);
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
        view.setBackgroundResource(R.drawable.calendar_cel_selectl);
        return view;
    }

    public int getFirstDayOfMonth(){
        return calendarRef.get(Calendar.DAY_OF_WEEK);
    }

    public int getMaxWeekInMonth(){
        return calendarRef.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }


}


