package com.sdpcrew.android.flatapp.BillsManager;

import android.support.v4.app.Fragment;
import com.sdpcrew.android.flatapp.*;
import com.sdpcrew.android.flatapp.TasksManager.SingleFragmentActivity;

/**
 * Created by David on 20/09/2016.
 */
public class BillListActivity extends SingleFragmentActivityBill {

    @Override
    protected Fragment createFragment() {
        return new BillListFragment();
    }

}
