package com.sdpcrew.android.flatapp.database;

/**
 * Created by David on 4/10/2016.
 */

public class BillDbSchema {

    //Only present to define String constants needed to describe moving pieces of table definition
    public static final class BillTable {
        public static final String NAME = "bills";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DUE_DATE = "due_date";
            public static final String AMOUNT = "amount";
            public static final String PAID = "paid";
        }
    }

}
