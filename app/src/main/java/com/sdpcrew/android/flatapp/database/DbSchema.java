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
            public static final String ID = "qualifier_id";
            public static final String TITLE = "qualifier_title";

        }

    }

    public static final class TaskTable {
        public static final String NAME = "task";

        public static final class Cols {
            public static final String QUALIFIER_ID = "qualifier_title";
            public static final String ID = "task_id";
            public static final String TITLE = "task_title";
            public static final String COMPLETED = "task_completed";

        }

    }

    public static final class ShoppingListsTable {
        public static final String NAME = "shopping_lists";

        public static final class Cols {
            public static final String ID = "list_id";
            public static final String TITLE = "list_title";
        }

    }

    public static final class ShoppingItemsTable {
        public static final String NAME = "shopping_items";

        public static final class Cols {
            public static final String SHOPPING_LIST_ID = "list_id";
            public static final String ID = "item_id";
            public static final String TITLE = "item_title";
        }

    }

}
