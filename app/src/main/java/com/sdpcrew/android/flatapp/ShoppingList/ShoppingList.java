package com.sdpcrew.android.flatapp.ShoppingList;

import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import com.sdpcrew.android.flatapp.Database.AllCursorWrapper;
import com.sdpcrew.android.flatapp.Database.DbSchema.ShoppingItemsTable;
import com.sdpcrew.android.flatapp.Database.MySqlConnection;
import com.sdpcrew.android.flatapp.Database.QueryMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.Splash.mDatabase;

/**
 * Created by Shane Birdsall on 1/09/2016.
 * Shopping list class which represents a list of items.
 */
public class ShoppingList implements Comparable<ShoppingList> {

    private UUID mId;
    private String listName;
    private ArrayList<Item> list;

    private static final String whereClause = ShoppingItemsTable.Cols.SHOPPING_LIST_ID + " =? AND " +
            ShoppingItemsTable.Cols.ID + " =?";

    public ShoppingList(String name) {
        this(UUID.randomUUID(), name);
    }

    public ShoppingList(UUID id, String name) {
        mId = id;
        setListName(name);
        list = new ArrayList<>();
    }

    public UUID getId() {
        return mId;
    }

    public void setListName(String name) {
        if (name != null && !(name.trim().equals(""))) {
            listName = name;
        } else listName = "???";
    }

    public String getListName() {
        return listName;
    }

    public List<Item> getListOfItems() {
        list.clear();
        AllCursorWrapper cursor = QueryMethods.queryDb(ShoppingItemsTable.NAME,
                ShoppingItemsTable.Cols.SHOPPING_LIST_ID + "=?", new String[]{this.mId.toString()}
        );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getItem());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    List<String> getListOfItemsByName() {
        List<String> items = new ArrayList<>();
        list.clear();
        AllCursorWrapper cursor = QueryMethods.queryDb(ShoppingItemsTable.NAME,
                ShoppingItemsTable.Cols.SHOPPING_LIST_ID + "=?", new String[]{this.mId.toString()}
        );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Item newItem = cursor.getItem();
                list.add(newItem);
                items.add(newItem.getItemName());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return items;
    }

    Item getItemByIndex(int index) {
        return list.get(index);
    }

    public void addItemToList(Item item) {
        try {
            list.add(item);
            ContentValues values = getContentValues(item);
            mDatabase.insert(ShoppingItemsTable.NAME, null, values);
        } catch (SQLiteConstraintException e) {
        } // Ignore
    }

    public void deleteItemFromList(Item item) {
        list.remove(item);
        mDatabase.delete(ShoppingItemsTable.NAME, whereClause,
                new String[]{this.mId.toString(), item.getId().toString()});
        new DeleteItem().execute(item);
    }

    public void updateItem(Item item) {
        ContentValues values = getContentValues(item);
        mDatabase.update(ShoppingItemsTable.NAME, values, whereClause,
                new String[]{this.mId.toString(), item.getId().toString()});
    }

    private ContentValues getContentValues(Item item) {
        ContentValues values = new ContentValues();
        values.put(ShoppingItemsTable.Cols.SHOPPING_LIST_ID, this.mId.toString());
        values.put(ShoppingItemsTable.Cols.ID, item.getId().toString());
        values.put(ShoppingItemsTable.Cols.TITLE, item.getItemName());
        return values;
    }

    @Override
    public int compareTo(ShoppingList o) {
        return listName.compareTo(o.listName);
    }

    private class DeleteItem extends AsyncTask<Item, Void, Void> {
        @Override
        protected final Void doInBackground(Item... params) {
            if (params[0] != null) {
                MySqlConnection mSql = new MySqlConnection();
                mSql.deleteItem(listName, params[0]);
                mSql.closeConnections();
            }
            return null;
        }
    }
}
