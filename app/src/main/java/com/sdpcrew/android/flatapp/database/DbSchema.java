package com.sdpcrew.android.flatapp.database;

/**
 * Created by David on 4/10/2016.
 */
//Only present to define String constants needed to describe moving pieces of table definitio
public class DbSchema {

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

    public static final class QualifierTable {
        public static final String NAME = "qualifier";

        public static final class Cols {
            public static final String ID = "id";
            public static final String TITLE = "title";

        }

    }

    public static final class TaskTable {
        public static final String NAME = "task";

        public static final class Cols {
            public static final String QUALIFIER_TITLE = "qualifier_title";
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String COMPLETED = "completed";

        }

    }

    public static final class TaskManager {
        public static final String NAME = "taskmanager";

        public static final class Cols {
            public static final String QUALIFIER_TITLE = "qualifier_title";
            public static final String TASK_ID = "taskid";
        }

    }

}
