package com.sdpcrew.android.flatapp.TasksManager;

import android.content.ContentValues;
import android.content.Context;

import com.sdpcrew.android.flatapp.Database.AllCursorWrapper;
import com.sdpcrew.android.flatapp.Database.QueryMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.Splash.mDatabase;
import static com.sdpcrew.android.flatapp.Database.DbSchema.*;

/**
 * QualifierLab is a Singleton class, it has methods to manage qualifiers(Creation,deletion,insertion,update).
 * Each qualifier is unique
 */
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

    /**
     * Add a new qualifier to the local database
     */
    public boolean addQualifier(Qualifier qualifier) {
        if (!containQualifier(qualifier.getTitle())) {
            ContentValues values = getContentValues(qualifier);
            mDatabase.insert(QualifierTable.NAME, null, values);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Remove a new qualifier to the local database
     */
    public boolean removeQualifier(Qualifier qualifier) {
        return mDatabase.delete(QualifierTable.NAME, whereClause,
                new String[]{qualifier.getId().toString()}) != 0;
    }
    /**
     * Update a qualifier in the local database
     */
    public void updateQualifier(Qualifier qualifier) {
        ContentValues values = getContentValues(qualifier);
        mDatabase.update(QualifierTable.NAME, values, whereClause,
                new String[]{qualifier.getId().toString()});
    }

    /**
     * Check if qualifier exists already
     */
    public boolean containQualifier(String name) {
        AllCursorWrapper cursor = this.queryQualifier(
                QualifierTable.Cols.TITLE + "=?", new String[]{name}
        );
        boolean exists = cursor.getCount() == 0;
        cursor.close();
        return !exists;
    }

    /**
     * Get a qualifier by UUID from local database
     */
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

    /**
     * Return a list of all qualifiers from the local database
     */
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

    /**
     * Create a ContentValue of all parameters of a qualifier
     */
    private ContentValues getContentValues(Qualifier qualifier) {
        ContentValues values = new ContentValues();
        values.put(QualifierTable.Cols.ID, qualifier.getId().toString());
        values.put(QualifierTable.Cols.TITLE, qualifier.getTitle());
        return values;
    }

    /**
     * Call static method QueryMethods.queryDb. This is a wrapper method
     */
    private AllCursorWrapper queryQualifier(String whereClause, String[] whereArgs) {

        return QueryMethods.queryDb(QualifierTable.NAME,whereClause,whereArgs);
    }

}
