package com.sdpcrew.android.flatapp.BillsManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.sdpcrew.android.flatapp.*;

import java.util.List;

/**
 * Created by David on 20/09/2016.
 */
public class BillListFragment extends Fragment{

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mBillRecyclerView;
    private BillAdapter mAdapter;
    private boolean mSubtitleVisible;

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

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_bill_list, menu);

        mSubtitleVisible = true;
        updateSubtitle();

        /*MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_bill:
                Bill bill = new Bill();
                BillLab.get(getActivity()).addBill(bill);
                Intent intent = BillPagerActivity.newIntent(getActivity(), bill.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        BillLab billLab = BillLab.get(getActivity());

        int billCount = billLab.getBills().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, billCount, billCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        BillLab billLab = BillLab.get(getActivity());
        List<Bill> bills = billLab.getBills();

        if(mAdapter == null){
            mAdapter = new BillAdapter(bills);
            mBillRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        //Goes through the list and removes incomplete bills that don't fit criteria
        billLab.rejectIncompleteBill();

        updateSubtitle();
    }

    private class BillHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mPaidCheckBox;
        private TextView mAmountTextView;
        //private FloatingActionButton mNewBillButton;

        private Bill mBill;

        public BillHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_bill_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_bill_date_text_view);
            mPaidCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_bill_paid_check_box);
            mAmountTextView = (TextView) itemView.findViewById(R.id.list_item_bill_amount_text_view);
            //mNewBillButton = (FloatingActionButton) itemView.findViewById(R.id.fragment_bill_add);
        }

        public void bindBill (Bill bill) {
            mBill = bill;
            mTitleTextView.setText(mBill.getTitle());
            mDateTextView.setText("Due: " + android.text.format.DateFormat.format("dd-MM-yyyy", mBill.getDate()));
            mPaidCheckBox.setChecked(mBill.isPaid());
            mAmountTextView.setText(mBill.getAmount());
        }

        public void onClick (View v) {
            Intent intent = BillPagerActivity.newIntent(getActivity(), mBill.getId());
            startActivity(intent);
        }
    }

    private class BillAdapter extends RecyclerView.Adapter<BillHolder> {
        private List<Bill> mBills;

        public BillAdapter(List<Bill> bills) {
            mBills = bills;
        }

        @Override
        public BillHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_bill, parent, false);
            return new BillHolder(view);
        }

        @Override
        public void onBindViewHolder(BillHolder holder, int position) {
            Bill bill = mBills.get(position);
            holder.bindBill(bill);
        }

        @Override
        public int getItemCount() {
            return mBills.size();
        }
    }




}
