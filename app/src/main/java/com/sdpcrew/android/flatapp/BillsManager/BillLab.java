package com.sdpcrew.android.flatapp.BillsManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sdpcrew.android.flatapp.*;
import com.sdpcrew.android.flatapp.database.BillBaseHelper;
import com.sdpcrew.android.flatapp.database.BillCursorWrapper;
import com.sdpcrew.android.flatapp.database.BillDbSchema.BillTable;

/**
 * Created by David on 20/09/2016.
 * BillLab is the location where all bills are stored. It is created through the singleton method
 * so as to ensure all bills are located in one place
 */
public class BillLab {

    private static BillLab sBillLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static BillLab get(Context context) {
        if (sBillLab == null) {
            sBillLab = new BillLab(context);
        }
        return sBillLab;
    }

    /**
     * Creates/Opens the bill database
     * @param context
     */
    private BillLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BillBaseHelper(mContext).getWritableDatabase();
    }

    public void addBill(Bill b) {
        ContentValues values = getContentValues(b);

        mDatabase.insert(BillTable.NAME, null, values);
    }

    public List<Bill> getBills() {
        List<Bill> bills = new ArrayList<>();

        BillCursorWrapper cursor = queryBills(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                bills.add(cursor.getBill());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return bills;
    }

    public Bill getBill(UUID id) {
        BillCursorWrapper cursor = queryBills(
                BillTable.Cols.UUID + " = ?", new String[] { id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBill();
        } finally {
            cursor.close();
        }
    }

    public void updateBill(Bill bill) {
        String uuidString = bill.getId().toString();
        ContentValues values = getContentValues(bill);

        mDatabase.update(BillTable.NAME, values, BillTable.Cols.UUID + " = ?", new String[] { uuidString });
    }

    public void deleteBill(Bill bill) {
        String[] whereArg = new String[] {bill.getId().toString()};
        String whereClause = "uuid" + "=?";

        mDatabase.delete(BillTable.NAME, whereClause, whereArg);
    }

    private static ContentValues getContentValues(Bill bill) {
        ContentValues values = new ContentValues();
        values.put(BillTable.Cols.UUID, bill.getId().toString());
        values.put(BillTable.Cols.TITLE, bill.getTitle());
        values.put(BillTable.Cols.DESCRIPTION, bill.getDescription());
        values.put(BillTable.Cols.DUE_DATE, bill.getDate().getTime());
        values.put(BillTable.Cols.AMOUNT, bill.getAmount());
        values.put(BillTable.Cols.PAID, bill.isPaid() ? 1 : 0);

        return values;
    }

    private BillCursorWrapper queryBills(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
             BillTable.NAME,
                null,  // Columns (null selects all columns)
                whereClause,
                whereArgs,
                null,  //groupBy
                null,  //having
                null   //orderBy
        );

        return new BillCursorWrapper(cursor);
    }



}
