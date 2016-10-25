package com.sdpcrew.android.flatapp.BillsManager;

import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;

import com.sdpcrew.android.flatapp.Database.AllCursorWrapper;
import com.sdpcrew.android.flatapp.Database.DbSchema.BillTable;
import com.sdpcrew.android.flatapp.Database.QueryMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.Splash.mDatabase;

/**
 * Created by David on 20/09/2016.
 * BillLab is the location where all bills are stored. It is created through the singleton method
 * so as to ensure all bills are located in one place
 */
public class BillLab {

    private static BillLab sBillLab; // Singleton. Creates a single instance of BillLab that will get referenced back
    private Context mContext;

    /**
     * Constructor checks to see if an instance already exists. If it does, returns it, otherwise
     * creates a new one
     * @param context
     * @return
     */
    public static BillLab get(Context context) {
        if (sBillLab == null) {
            sBillLab = new BillLab(context);
        }
        return sBillLab;
    }

    /**
     * Creates/Opens the bill database
     */
    private BillLab(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * Adds the passed bill to the database via filling db Contents in contentvalues.
     * @param b
     */
    public void addBill(Bill b) {
        ContentValues values = getContentValues(b);
        mDatabase.insert(BillTable.NAME, null, values);
    }

    /**
     * Retrieves the bills from the local database and puts them into an ArrayList
     * @return
     */
    public List<Bill> getBills() {
        List<Bill> bills = new ArrayList<>();

        AllCursorWrapper cursor = queryBills(null, null);

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

    /**
     * Retrieves the bill with the specified UUID, otherwise will return null
     * @param id
     * @return
     */
    public Bill getBill(UUID id) {
        //Moves the cursor to the specified bill if it is in the list
        AllCursorWrapper cursor = queryBills(
                BillTable.Cols.UUID + " = ?", new String[] { id.toString()}
        );
        //If the return is empty, returns null otherwise returns the bill
        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getBill();
        } finally {
            //Close the cursor for data integrity
            cursor.close();
        }
    }

    /**
     * Retrieves the photo stored in the external files directory, currently where all the bills are
     * stored
     * @param bill
     * @return
     */
    public File getPhotoFile(Bill bill) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, bill.getPhotoFilename());
    }

    /**
     * Method used to update bills to the database through the content values method to ensure correct
     * formatting
     * @param bill
     */
    public void updateBill(Bill bill) {
        String uuidString = bill.getId().toString();
        ContentValues values = getContentValues(bill);

        mDatabase.update(BillTable.NAME, values, BillTable.Cols.UUID + " = ?", new String[] { uuidString });
    }

    /**
     * Method deletes bills based on UUID of the passed bill
     * @param bill
     */
    public void deleteBill(Bill bill) {
        String[] whereArg = new String[] {bill.getId().toString()};
        String whereClause = "uuid" + "=?";

        mDatabase.delete(BillTable.NAME, whereClause, whereArg);
    }

    /**
     * Method ensures that all database queries are in correct format. Also improves reusability of code
     * @param bill
     * @return
     */
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

    /**
     * Method used to access the database and pick out bills.
     * @param whereClause
     * @param whereArgs
     * @return
     */
    private AllCursorWrapper queryBills(String whereClause, String[] whereArgs) {
        return QueryMethods.queryDb(BillTable.NAME,whereClause,whereArgs);
    }
}
