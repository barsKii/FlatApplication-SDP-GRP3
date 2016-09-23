package com.sdpcrew.android.flatapp.BillsManager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.sdpcrew.android.flatapp.*;

import com.sdpcrew.android.flatapp.TasksManager.SingleFragmentActivity;

import java.util.UUID;

/**
 * Created by David on 20/09/2016.
 */
public class BillActivity extends SingleFragmentActivityBill {

    private static final String EXTRA_BILL_ID = "com.sdpcrew.android.flatapplication.bill_id";

    public static Intent newIntent(Context packageContext, UUID billId) {
        Intent intent = new Intent(packageContext, BillActivity.class);
        intent.putExtra(EXTRA_BILL_ID, billId);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        UUID billId = (UUID) getIntent().getSerializableExtra(EXTRA_BILL_ID);
        return BillFragment.newInstance(billId);
    }

}
