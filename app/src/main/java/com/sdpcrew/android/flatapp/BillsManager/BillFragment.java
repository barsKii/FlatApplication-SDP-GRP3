package com.sdpcrew.android.flatapp.BillsManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.sdpcrew.android.flatapp.*;

import java.util.UUID;

/**
 * Created by David on 20/09/2016.
 */
public class BillFragment extends Fragment {

    private static final String ARG_BILL_ID = "bill_id";

    private Bill mBill;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mPaidCheckBox;

    public static BillFragment newInstance (UUID billId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BILL_ID, billId);

        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        UUID billId = (UUID) getArguments().getSerializable(ARG_BILL_ID);

        mBill = BillLab.get(getActivity()).getBill(billId);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bill, container, false);

        mTitleField = (EditText) v.findViewById(R.id.bill_title);
        mTitleField.setText(mBill.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBill.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.bill_date);
        mDateButton.setText(android.text.format.DateFormat.format("dd-MM-yyyy", mBill.getDate()));
        mDateButton.setEnabled(false);

        mPaidCheckBox = (CheckBox) v.findViewById(R.id.bill_paid);
        mPaidCheckBox.setChecked(mBill.isPaid());
       /* mPaidCheckBox.setOnClickListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBill.setPaid(isChecked);
            }

        });*/

        return v;
    }

}
