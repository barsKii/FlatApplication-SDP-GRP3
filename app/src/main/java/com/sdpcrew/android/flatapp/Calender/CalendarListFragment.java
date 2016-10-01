package com.sdpcrew.android.flatapp.Calender;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdpcrew.android.flatapp.BillsManager.Bill;
import com.sdpcrew.android.flatapp.BillsManager.BillLab;
import com.sdpcrew.android.flatapp.BillsManager.BillListFragment;
import com.sdpcrew.android.flatapp.R;

import java.util.List;

/**
 * Created by iAmacone on 30/09/16.
 */

public class CalendarListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mBillRecyclerView;

    private boolean mSubtitleVisible;

    private CalendarAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_list, container, false);

        mBillRecyclerView = (RecyclerView) view.findViewById(R.id.bill_recycler_view);
        mBillRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }



        return view;
    }

    /**
     * Updates the list of bills to be displayed by the UI
     */

}
