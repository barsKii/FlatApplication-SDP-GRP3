package com.sdpcrew.android.flatapp.BillsManager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.sdpcrew.android.flatapp.*;

import java.io.File;
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
    private static final String DIALOG_IMAGE = "image";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    private Bill mBill;
    private File mPhotoFile;
    private EditText mTitleField;
    private EditText mDescriptionField;
    private Button mDateButton;
    private CheckBox mPaidCheckBox;
    private EditText mAmountField;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;


    public static BillFragment newInstance(UUID billId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BILL_ID, billId);

        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Retrieves a bill from the bill lab to be represented by this fragment
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    //Options menu to delete the bill
        UUID billId = (UUID) getArguments().getSerializable(ARG_BILL_ID);
        mBill = BillLab.get(getActivity()).getBill(billId);
        mPhotoFile = BillLab.get(getActivity()).getPhotoFile(mBill);
    }

    /**
     * When the activity is changed, if the required fields are completed, will update bill,
     * if not will delete the bill
     */
    @Override
    public void onPause() {
        super.onPause();
        //Checks the status of title and amount and responds appropriately
        if (mBill.getTitle() != null && !mBill.getTitle().isEmpty() && mBill.getAmount() != null &&
                !mBill.getAmount().isEmpty()) {
            BillLab.get(getActivity()).updateBill(mBill);
        } else {
            BillLab.get(getActivity()).deleteBill(mBill);
        }
    }

    /**
     * Inflates a menu with two icons, one to save the bill and the other to delete it
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_bill, menu);
    }

    /**
     * Records the responses to menu items selected.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_bill:
                //Navigates to the parent activity listed in manifest. In this case, BillListActivity
                //Works the same as the up button, just a visual cue as users don't know back end
                NavUtils.navigateUpFromSameTask(this.getActivity());
                return true;
            case R.id.menu_item_delete_bill:
                //Deletes the bill from the database and then returns to the bill list
                BillLab.get(getActivity()).deleteBill(mBill);
                NavUtils.navigateUpFromSameTask(this.getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Initializes the elements that will be viewed in the fragment view. Creates listeners for each
     * item as well.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
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
                if (TextUtils.isEmpty(s.toString())) {
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

        mDescriptionField = (EditText) v.findViewById(R.id.bill_description);
        mDescriptionField.setText(mBill.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBill.setDescription(s.toString());
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
                if (!s.toString().equals(current)) {
                    mAmountField.removeTextChangedListener(this);

                    //Gets the symbol for the current currency on the phone to implement the correct currency symbol
                    String replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR)
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

        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.bill_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.bill_photo);
        updatePhotoView();
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoFile == null || !mPhotoFile.exists()) {
                    return;
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ImageFragment.createInstance(mPhotoFile.getAbsolutePath()).show(fm, DIALOG_IMAGE);
            }


        });

        return v;
    }

    /**
     * Utilized for receiving date back from DatePickerFragment
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBill.setDate(date);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    private void updateDate() {
        mDateButton.setText(android.text.format.DateFormat.format("dd-MM-yyyy", mBill.getDate()));
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            BitmapDrawable bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageDrawable(bitmap);
        }
    }

}
