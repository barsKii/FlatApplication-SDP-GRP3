package com.sdpcrew.android.flatapp.ShoppingList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sdpcrew.android.flatapp.TasksManager.QualifierLab;
import com.sdpcrew.android.flatapp.database.AllCursorWrapper;
import com.sdpcrew.android.flatapp.database.DbSchema.ShoppingListsTable;
import com.sdpcrew.android.flatapp.database.QueryMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.MainActivity.mDatabase;

/**
 * Created by vini on 18/10/16.
 */

public class ShoppingListLab {
    private static final String whereClause = ShoppingListsTable.Cols.ID + "=?";
    private static ShoppingListLab sShoppingListLab;
    private Context mContext;

    public static ShoppingListLab get(Context context) {
        if (sShoppingListLab == null) {
            sShoppingListLab = new ShoppingListLab(context);
        }
        return sShoppingListLab;
    }

    private ShoppingListLab(Context context) {
        mContext = context.getApplicationContext();
    }

    public boolean addShoppingList(ShoppingList shoppingList) {
        ContentValues values = getContentValues(shoppingList);
        return mDatabase.insert(ShoppingListsTable.NAME, null, values) == 0;

    }

    public boolean removeShoppingList(ShoppingList shoppingList) {
        return mDatabase.delete(ShoppingListsTable.NAME, whereClause,
                new String[]{shoppingList.getId().toString()}) != 0;
    }

    public void updateShoppingList(ShoppingList shoppingList) {
        ContentValues values = getContentValues(shoppingList);
        mDatabase.update(ShoppingListsTable.NAME, values, whereClause,
                new String[]{shoppingList.getId().toString()});
    }

    public ShoppingList getShoppingList(UUID id) {
        AllCursorWrapper cursor = this.queryShoppingList(
                whereClause, new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getList();
        } finally {
            cursor.close();
        }
    }

    public List<ShoppingList> getShoppingLists() {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        AllCursorWrapper cursor = this.queryShoppingList(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                shoppingLists.add(cursor.getList());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return shoppingLists;

    }

    public List<String> getShoppingListsNames() {
        List<String> shoppingLists = new ArrayList<>();
        AllCursorWrapper cursor = this.queryShoppingList(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                shoppingLists.add(cursor.getList().getListName());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return shoppingLists;

    }

    private ContentValues getContentValues(ShoppingList shoppingList) {
        ContentValues values = new ContentValues();
        values.put(ShoppingListsTable.Cols.ID, shoppingList.getId().toString());
        values.put(ShoppingListsTable.Cols.TITLE, shoppingList.getListName());
        return values;
    }

    private AllCursorWrapper queryShoppingList(String whereClause, String[] whereArgs) {

        return QueryMethods.queryDb(ShoppingListsTable.NAME,whereClause,whereArgs);
    }
}
