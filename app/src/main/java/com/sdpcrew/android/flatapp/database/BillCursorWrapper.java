package com.sdpcrew.android.flatapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sdpcrew.android.flatapp.BillsManager.Bill;
import com.sdpcrew.android.flatapp.database.BillDbSchema.BillTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by David on 4/10/2016.
 */

public class BillCursorWrapper extends CursorWrapper {
    public BillCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Bill getBill() {
        String uuidString = getString(getColumnIndex(BillTable.Cols.UUID));
        String title = getString(getColumnIndex(BillTable.Cols.TITLE));
        String description = getString(getColumnIndex(BillTable.Cols.DESCRIPTION));
        long date = getLong(getColumnIndex(BillTable.Cols.DUE_DATE));
        String amount = getString(getColumnIndex(BillTable.Cols.AMOUNT));
        int isPaid = getInt(getColumnIndex(BillTable.Cols.PAID));

        Bill bill = new Bill(UUID.fromString(uuidString));
        bill.setTitle(title);
        bill.setDescription(description);
        bill.setDate(new Date(date));
        bill.setAmount(amount);
        bill.setPaid(isPaid != 0);

        return bill;
    }
}
