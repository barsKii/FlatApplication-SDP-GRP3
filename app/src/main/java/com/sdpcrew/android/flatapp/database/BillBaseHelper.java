package com.sdpcrew.android.flatapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sdpcrew.android.flatapp.database.BillDbSchema.BillTable;

/**
 * Created by David on 4/10/2016.
 * Checks to see if a the Database exists, if not, creates it, the tables and initial data it needs.
 * If it does exist, it opens it and checks the version, if it is an older version, runs code to upgrade
 * to newer version.
 */

public class BillBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "billBase.db";

    public BillBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BillTable.NAME + "( _id integer primary key autoincrement, " +
                BillTable.Cols.UUID + ", " +
                BillTable.Cols.TITLE + ", " +
                BillTable.Cols.DESCRIPTION + ", " +
                BillTable.Cols.DUE_DATE + ", " +
                BillTable.Cols.AMOUNT + ", " +
                BillTable.Cols.PAID +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
