package com.sdpcrew.android.flatapp.BillsManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    private RecyclerView mBillRecyclerView;
    private BillAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_list, container, false);

        mBillRecyclerView = (RecyclerView) view.findViewById(R.id.bill_recycler_view);
        mBillRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
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
    }

    private class BillHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mPaidCheckBox;

        private Bill mBill;

        public BillHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_bill_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_bill_date_text_view);
            mPaidCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_bill_paid_check_box);
        }

        public void bindBill (Bill bill) {
            mBill = bill;
            mTitleTextView.setText(mBill.getTitle());
            mDateTextView.setText(mBill.getDate().toString());
            mPaidCheckBox.setChecked(mBill.isPaid());
        }

        public void onClick (View v) {
            Intent intent = BillActivity.newIntent(getActivity(), mBill.getId());
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
