package com.sdpcrew.android.flatapp.BillsManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
 * BillListFragment utilizes RecyclerView to illustrate multiple BillFragments at once and
 * make the list scrollable without delay
 */
public class BillListFragment extends Fragment{

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mBillRecyclerView; //The Recycler view to allow multiple bills appear in list
    private BillAdapter mAdapter; //Adapts the bills
    private boolean mSubtitleVisible; //Subtitle shows how many bills there are

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Sets up the Recycler View and sets the layout manager
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

    /**
     * UpdateUI is called as onResume() is called when ever the view is accessed or returned to and
     * will provide an up to date list of BillFragments
     */
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

    /**
     * Provides the bill count and the back arrow
     * @param menu:
     * @param inflater:
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_bill_list, menu);

        mSubtitleVisible = true;
        updateSubtitle();
    }

    /**
     * Relates to the selecting of a fragment and starting that BillFragment as a new activity
     * through the BillPagerActivity
     * @param item:
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_bill:
                Bill bill = new Bill();
                //Adds the bill to the database and retrieves it to be worked on
                BillLab.get(getActivity()).addBill(bill);
                //Opens a new intent to work on the bill
                Intent intent = BillPagerActivity.newIntent(getActivity(), bill.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Provides the subtitle count of current bills. Has nullable option for potential future
     * to hide the subtitle if user wishes.
     */
    private void updateSubtitle() {
        BillLab billLab = BillLab.get(getActivity());

        int billCount = billLab.getBills().size();
        // Quantity string used to represent the bill gramatically correctly
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, billCount, billCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    /**
     * Updates the list of bills to be displayed by the UI
     */
    private void updateUI() {
        BillLab billLab = BillLab.get(getActivity());
        List<Bill> bills = billLab.getBills();
        //Sets adapter if not existing otherwise updates information
        if(mAdapter == null){
            mAdapter = new BillAdapter(bills);
            mBillRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setBills(bills);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    /**
     * Provides the functionality to show each individual bill in a small fragment to fit multiple
     * ones on a single screen and make them clickable
     */
    private class BillHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mPaidCheckBox;
        private TextView mAmountTextView;

        private Bill mBill;

        /**
         * Binds the views to the variables
         * @param itemView
         */
        BillHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_bill_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_bill_date_text_view);
            mPaidCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_bill_paid_check_box);
            mAmountTextView = (TextView) itemView.findViewById(R.id.list_item_bill_amount_text_view);
        }

        /**
         * Sets the views to the content retrieved from the database
         * @param bill
         */
        void bindBill(Bill bill) {
            mBill = bill;
            mTitleTextView.setText(mBill.getTitle());
            mDateTextView.setText(getString(R.string.due) + android.text.format.DateFormat.format("dd-MM-yyyy", mBill.getDate()));
            mPaidCheckBox.setChecked(mBill.isPaid());
            mAmountTextView.setText(mBill.getAmount());
        }

        /**
         * Starts the intent on click
         * @param v
         */
        public void onClick (View v) {
            Intent intent = BillPagerActivity.newIntent(getActivity(), mBill.getId());
            startActivity(intent);
        }
    }

    /**
     * Bill Adapter provides the holding mechanism of each bill fragment in the list and manages items
     * such as the count of items
     */
    private class BillAdapter extends RecyclerView.Adapter<BillHolder> {
        private List<Bill> mBills;

        BillAdapter(List<Bill> bills) {
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

        //Refreshes the bills that it displays
        void setBills(List<Bill> bills) {
            mBills = bills;
        }
    }




}
