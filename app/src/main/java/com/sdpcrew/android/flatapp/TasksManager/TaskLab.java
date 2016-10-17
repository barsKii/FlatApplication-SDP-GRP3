package com.sdpcrew.android.flatapp.TasksManager;

import android.content.ContentValues;
import android.database.Cursor;

import com.sdpcrew.android.flatapp.database.AllCursorWrapper;
import com.sdpcrew.android.flatapp.database.DbSchema.TaskTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.MainActivity.mDatabase;

public class TaskLab {

    private String mQualifierTitle;
    private static final String whereClause = TaskTable.Cols.QUALIFIER_TITLE + "=? AND " + TaskTable.Cols.ID + " = ?";


    public TaskLab(String title) {
        mQualifierTitle = title;
    }

    public boolean addTask(Task task) {
        ContentValues values = getContentValues(task);
        return mDatabase.insert(TaskTable.NAME, null, values) != -1;


    }

    public boolean removeTask(Task task) {
        String[] whereArg = new String[]{mQualifierTitle, task.getId().toString()};
        return mDatabase.delete(TaskTable.NAME, whereClause, whereArg) != 0;

    }

    public void updateTask(Task task) {
        String[] whereArg = new String[]{mQualifierTitle, task.getId().toString()};
        ContentValues values = getContentValues(task);
        mDatabase.update(TaskTable.NAME, values, whereClause, whereArg);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        AllCursorWrapper cursor = this.queryTask(TaskTable.Cols.QUALIFIER_TITLE + "= ?",
                new String[]{mQualifierTitle});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return tasks;

    }

    public Task getTask(UUID id) {
        AllCursorWrapper cursor = this.queryTask(
                whereClause, new String[]{mQualifierTitle, id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTask();
        } finally {
            cursor.close();
        }
    }

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.QUALIFIER_TITLE, mQualifierTitle);
        values.put(TaskTable.Cols.ID, task.getId().toString());
        values.put(TaskTable.Cols.TITLE, task.getTitle());
        values.put(TaskTable.Cols.COMPLETED, task.isCompleted());
        return values;
    }

    private AllCursorWrapper queryTask(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
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
