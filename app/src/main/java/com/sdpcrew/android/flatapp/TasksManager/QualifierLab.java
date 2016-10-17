package com.sdpcrew.android.flatapp.TasksManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sdpcrew.android.flatapp.database.BaseHelper;
import com.sdpcrew.android.flatapp.database.AllCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.MainActivity.mDatabase;
import static com.sdpcrew.android.flatapp.database.DbSchema.*;

public class QualifierLab {
    private static final String whereClause = QualifierTable.Cols.ID + "=?";

    private static QualifierLab sQualifierLab;
    private Context mContext;

    public static QualifierLab get(Context context) {
        if (sQualifierLab == null) {
            sQualifierLab = new QualifierLab(context);
        }
        return sQualifierLab;
    }

    private QualifierLab(Context context) {
        mContext = context.getApplicationContext();
    }

    public boolean addQualifier(Qualifier qualifier) {
        if (!containQualifier(qualifier.getTitle())) {
            ContentValues values = getContentValues(qualifier);
            mDatabase.insert(QualifierTable.NAME, null, values);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeQualifier(Qualifier qualifier) {
        return mDatabase.delete(QualifierTable.NAME, whereClause,
                new String[]{qualifier.getId().toString()}) != 0;
    }

    public void updateQualifier(Qualifier qualifier) {
        ContentValues values = getContentValues(qualifier);
        mDatabase.update(QualifierTable.NAME, values, whereClause,
                new String[]{qualifier.getId().toString()});
    }

    public boolean containQualifier(String name) {
        AllCursorWrapper cursor = this.queryQualifier(
                QualifierTable.Cols.TITLE + "=?", new String[]{name}
        );
        boolean exists = cursor.getCount() == 0;
        cursor.close();
        return !exists;
    }

    public Qualifier getQualifier(UUID id) {
        AllCursorWrapper cursor = this.queryQualifier(
                whereClause, new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getQualifier();
        } finally {
            cursor.close();
        }
    }

    public List<Qualifier> getQualifiers() {
        List<Qualifier> qualifiers = new ArrayList<>();
        AllCursorWrapper cursor = this.queryQualifier(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                qualifiers.add(cursor.getQualifier());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return qualifiers;

    }

    private ContentValues getContentValues(Qualifier qualifier) {
        ContentValues values = new ContentValues();
        values.put(QualifierTable.Cols.ID, qualifier.getId().toString());
        values.put(QualifierTable.Cols.TITLE, qualifier.getTitle());
        return values;
    }

    private AllCursorWrapper queryQualifier(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                QualifierTable.NAME,
                null,  // Columns (null selects all columns)
                whereClause,
                whereArgs,
                null,  //groupBy
                null,  //having
                null   //orderBy
        );

        return new AllCursorWrapper(cursor);
    }

}
