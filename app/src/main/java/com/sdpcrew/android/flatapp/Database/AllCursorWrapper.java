package com.sdpcrew.android.flatapp.Database;

import android.database.Cursor;

import com.sdpcrew.android.flatapp.BillsManager.Bill;
import com.sdpcrew.android.flatapp.ShoppingList.Item;
import com.sdpcrew.android.flatapp.ShoppingList.ShoppingList;
import com.sdpcrew.android.flatapp.TasksManager.Qualifier;
import com.sdpcrew.android.flatapp.TasksManager.Task;
import com.sdpcrew.android.flatapp.Database.DbSchema.BillTable;

import java.util.Date;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.Database.DbSchema.*;

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
        String uuidString = getString(getColumnIndex(QualifierTable.Cols.ID));
        String title = getString(getColumnIndex(QualifierTable.Cols.TITLE));

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

    public ShoppingList getList() {
        String uuidString = getString(getColumnIndex(ShoppingListsTable.Cols.ID));
        String title = getString(getColumnIndex(ShoppingListsTable.Cols.TITLE));
        ShoppingList shoppingList = new ShoppingList(UUID.fromString(uuidString),title);
        return shoppingList;
    }

    public Item getItem() {
//        String uuidList = getString(getColumnIndex(ShoppingItemsTable.Cols.SHOPPING_LIST_ID));
        String uuidItem = getString(getColumnIndex(ShoppingItemsTable.Cols.ID));
        String title = getString(getColumnIndex(ShoppingItemsTable.Cols.TITLE));
        Item shoppingItem = new Item(UUID.fromString(uuidItem),title);
        return shoppingItem;
    }


}
