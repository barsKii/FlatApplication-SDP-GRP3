package com.sdpcrew.android.flatapp.BillsManager;

import android.support.v4.app.Fragment;

/**
 * Created by David on 20/09/2016.
 * The class initially called by MainActivity and starts the BillListFragment
 */
public class BillListActivity extends SingleFragmentActivityBill {

    @Override
    protected Fragment createFragment() {
        return new BillListFragment();
    }

}
