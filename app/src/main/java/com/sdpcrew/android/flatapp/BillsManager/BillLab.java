package com.sdpcrew.android.flatapp.BillsManager;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.sdpcrew.android.flatapp.*;

/**
 * Created by David on 20/09/2016.
 */
public class BillLab {

    private static BillLab sBillLab;

    private List<Bill> mBills;

    public static BillLab get (Context context) {
        if (sBillLab == null) {
            sBillLab = new BillLab(context);
        }
        return sBillLab;
    }

    private BillLab (Context context) {
        mBills = new ArrayList<>();
    }

    public void addBill (Bill b) {
        mBills.add(b);
    }

    public List<Bill> getBills() {
        return mBills;
    }

    public Bill getBill(UUID id) {
        for (Bill bill : mBills) {
            if (bill.getId().equals(id)) {
                return bill;
            }
        }
        return null;
    }

}
