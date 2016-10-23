package com.sdpcrew.android.flatapp.Database;

import android.database.Cursor;

import static com.sdpcrew.android.flatapp.Splash.mDatabase;

/**
 * Created by vini on 18/10/16.
 *
 */

public class QueryMethods {

    public static AllCursorWrapper queryDb(String tableName, String whereClause, String[] whereArgs) {
        Cursor cursor = null;
        if(mDatabase != null) {
             cursor = mDatabase.query(
                    tableName,
                    null,  // Columns (null selects all columns)
                    whereClause,
                    whereArgs,
                    null,  //groupBy
                    null,  //having
                    null   //orderBy
            );
        }
        return new AllCursorWrapper(cursor);
    }
}
