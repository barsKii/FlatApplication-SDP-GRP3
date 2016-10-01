package com.sdpcrew.android.flatapp.Calender;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.*;
import com.sdpcrew.android.flatapp.*;
import android.widget.EditText;
import android.text.*;


/**
 * Created by iAmacone on 1/10/16.
 */

public class CalendarFragment extends Fragment{

    private CalendarCon mCalendarCon;
    private EditText mCalendarTitleField;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCalendarCon = new CalendarCon();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_add_event, container, false);

        mCalendarTitleField = (EditText)v.findViewById(R.id.calendar_event_name);
        mCalendarTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCalendarCon.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });
        return v;
    }


}
