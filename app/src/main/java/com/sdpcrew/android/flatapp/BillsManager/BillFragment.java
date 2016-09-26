package com.sdpcrew.android.flatapp.BillsManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


import com.sdpcrew.android.flatapp.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by David on 20/09/2016.
 * Bill Fragment refers to the view when a bill is open.
 */
public class BillFragment extends Fragment {

    private static final String ARG_BILL_ID = "bill_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Bill mBill;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mPaidCheckBox;
    private EditText mAmountField;

    public static BillFragment newInstance (UUID billId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BILL_ID, billId);

        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Retrieves a bill from the bill lab to be represented by this fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        UUID billId = (UUID) getArguments().getSerializable(ARG_BILL_ID);

        mBill = BillLab.get(getActivity()).getBill(billId);
    }

    /**
     * Initializes the elements that will be viewed in the fragment view. Creates listeners for each
     * item as well.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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
                // Will not allow a title to be set to 0 length, will provide a warning if user
                //deletes to 0 length
                if(TextUtils.isEmpty(s.toString())) {
                    CharSequence err = getString(R.string.empty_title_warning);
                    mTitleField.setError(err);
                    return;
                } else {
                    mBill.setTitle(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.bill_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Functionality used to select date. Communicates between DatePickerFragment and
                //a created FragmentManager to complete the process
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mBill.getDate());
                dialog.setTargetFragment(BillFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mPaidCheckBox = (CheckBox) v.findViewById(R.id.bill_paid);
        mPaidCheckBox.setChecked(mBill.isPaid());
        mPaidCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property
                mBill.setPaid(isChecked);
            }
        });

        mAmountField = (EditText) v.findViewById(R.id.bill_amount);
        mAmountField.setText(mBill.getAmount());
        mAmountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            private String current = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Ensures that the money symbol for the selected user appears and formats to currency length
                if(!s.toString().equals(current)){
                    mAmountField.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[$,.]", "");

                    BigDecimal parsed = new BigDecimal(cleanString).setScale(2,BigDecimal.ROUND_FLOOR)
                            .divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed));

                    current = formatted;
                    mAmountField.setText(formatted);
                    mAmountField.setSelection(formatted.length());
                    mBill.setAmount(formatted);

                    mAmountField.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return v;
    }

    /**
     * Utilized for receiving date back from DatePickerFragment
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBill.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(android.text.format.DateFormat.format("dd-MM-yyyy", mBill.getDate()));
    }

}
