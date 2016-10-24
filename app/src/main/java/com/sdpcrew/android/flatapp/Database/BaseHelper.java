package com.sdpcrew.android.flatapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.sdpcrew.android.flatapp.Database.DbSchema.*;

/**
 * Created by David on 4/10/2016.
 * Checks to see if a the Database exists, if not, creates it, the tables and initial data it needs.
 * If it does exist, it opens it and checks the version, if it is an older version, runs code to upgrade
 * to newer version.
 */

public class BaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "billBase.db";

    public BaseHelper(Context context) {
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

        db.execSQL("create table " + QualifierTable.NAME + "( " +
                QualifierTable.Cols.ID+" primary key, "+
                QualifierTable.Cols.TITLE+ " )");

        db.execSQL("create table " + TaskTable.NAME + "( " +
                TaskTable.Cols.QUALIFIER_ID +" , "+
                TaskTable.Cols.ID+", "+
                TaskTable.Cols.TITLE+", "+
                TaskTable.Cols.COMPLETED+" INTEGER )");

        db.execSQL("create table " + ShoppingListsTable.NAME + "( " +
                ShoppingListsTable.Cols.ID+", "+
                ShoppingListsTable.Cols.TITLE+" primary key  )");

        db.execSQL("create table " + ShoppingItemsTable.NAME + "( " +
                ShoppingItemsTable.Cols.SHOPPING_LIST_ID+", "+
                ShoppingItemsTable.Cols.ID+" primary key, "+
                ShoppingItemsTable.Cols.TITLE+" )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
