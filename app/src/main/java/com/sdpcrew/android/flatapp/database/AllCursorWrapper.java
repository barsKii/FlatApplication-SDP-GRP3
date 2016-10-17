package com.sdpcrew.android.flatapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sdpcrew.android.flatapp.BillsManager.Bill;
import com.sdpcrew.android.flatapp.TasksManager.Qualifier;
import com.sdpcrew.android.flatapp.TasksManager.Task;
import com.sdpcrew.android.flatapp.database.DbSchema.BillTable;

import java.util.Date;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.database.DbSchema.*;

public class AllCursorWrapper extends android.database.CursorWrapper {
    public AllCursorWrapper(Cursor cursor) {
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

    public Qualifier getQualifier() {
        String uuidString = getString(getColumnIndex(TaskTable.Cols.ID));
        String title = getString(getColumnIndex(BillTable.Cols.TITLE));

        Qualifier qualifier = new Qualifier(UUID.fromString(uuidString));
        qualifier.setTitle(title);

        return qualifier;
    }

    public Task getTask() {
        String uuidString = getString(getColumnIndex(TaskTable.Cols.ID));
        String title = getString(getColumnIndex(TaskTable.Cols.TITLE));
        int completed = getInt(getColumnIndex(TaskTable.Cols.COMPLETED));

        Task task = new Task(UUID.fromString(uuidString));
        task.setTitle(title);
        if(completed == 1){
            task.setCompleted(true);
        }

        return task;
    }
}
