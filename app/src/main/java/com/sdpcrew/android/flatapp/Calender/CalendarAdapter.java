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

    private Calendar mCalendarRef;
    public Calendar mPrevMonth;
    public Calendar mMonth;
    private Calendar mCalendarClone;

    private View mPreviousView;
    private String mCurentDate;
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
        mCalendarRef = CalendarDetails; //save calendar from main to be handled
        mCalendarClone = (Calendar) CalendarDetails.clone(); //clone for changes, to refer to originally values passed

        mContext = c;
        mCalendarRef.set(Calendar.DAY_OF_MONTH,1);

        this.items = new ArrayList<String>();
        //get current date without interference of calendar class index values
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        mCurentDate = df.format(mCalendarClone.getTime());
        refreshDays();

    }


    /**
     * Build calender variables
     * such as max days in year and
     * offset to be display and passed to
     * calendar main
     */
    public void refreshDays() {
        /*items.clear(); will be changed in the next integration to if() statement filtering out intial start
           as items is working on a hard coded basis for testing.
         */

        items.clear();
        dayEvents.clear();// keep an eye on varibale for future
        mPrevMonth = (Calendar) mCalendarRef.clone();// clones for difference from next,prev clicks
        firstDayOfMonth = getFirstDayOfMonth();
        MaxWeekInMonth = getMaxWeekInMonth();
        MonthLength = MaxWeekInMonth * 7;// help make the grid
        DaysInAMonth = getMaxDaysInAMonth();
        offDays = DaysInAMonth - (firstDayOfMonth - 1);//E.G 31 -(5-1) 31-4 =27
        mMonth = (Calendar) mPrevMonth.clone();
        mMonth.set(Calendar.DAY_OF_MONTH, offDays + 1);//28

        //Build Grid
        for (int n = 0; n < MonthLength; n++) {

            itemvalue = df.format(mMonth.getTime());
            mMonth.add(Calendar.DATE, 1);
            dayEvents.add(itemvalue);

        }
    }

    public int getMaxDaysInAMonth() {
        int maxDays;
        if (mCalendarRef.get(Calendar.MONTH) == mCalendarRef.getActualMinimum(Calendar.MONTH)) {
            mPrevMonth.set((mCalendarRef.get(Calendar.YEAR) - 1), mCalendarRef.getActualMaximum(Calendar.MONTH), 1);
        } else {
            mPrevMonth.set(Calendar.MONTH, mCalendarRef.get(Calendar.MONTH) - 1);
        }
        maxDays = mPrevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
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

        if (dayEvents.get(position).equals(mCurentDate)) {
            setSelected(v);
            mPreviousView = v;
        } else {
            v.setBackgroundResource(R.drawable.list_item_background);
        }
        dayView.setText(gridvalue);

        String date = dayEvents.get(position);

        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthStr = "" + (mCalendarRef.get(Calendar.MONTH) + 1);
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
        if (mPreviousView != null) {
            mPreviousView.setBackgroundResource(R.drawable.list_item_background);
        }
        mPreviousView = view;
        view.setBackgroundResource(R.drawable.calendar_cel_selectl);
        return view;
    }

    public int getFirstDayOfMonth(){return mCalendarRef.get(Calendar.DAY_OF_WEEK);}

    public int getMaxWeekInMonth(){return mCalendarRef.getActualMaximum(Calendar.WEEK_OF_MONTH);}


}


