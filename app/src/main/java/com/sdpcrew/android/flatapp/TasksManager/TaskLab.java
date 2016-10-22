package com.sdpcrew.android.flatapp.TasksManager;

import android.content.ContentValues;

import com.sdpcrew.android.flatapp.Database.AllCursorWrapper;
import com.sdpcrew.android.flatapp.Database.DbSchema.TaskTable;
import com.sdpcrew.android.flatapp.Database.QueryMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sdpcrew.android.flatapp.Splash.mDatabase;

/**
 * TaskLab is a Singleton class, it has methods to manage qualifiers(Creation,deletion,insertion,update).
 * This class also stores the name of the parent qualifier
 */
public class TaskLab {

    private String mQualifierTitle;
    private static final String whereClause = TaskTable.Cols.QUALIFIER_ID + "=? AND " + TaskTable.Cols.ID + " = ?";

    public TaskLab(String title) {
        mQualifierTitle = title;
    }

    /**
     * Add a new Task to the local database
     */
    public boolean addTask(Task task) {
        ContentValues values = getContentValues(task);
        return mDatabase.insert(TaskTable.NAME, null, values) != -1;


    }

    /**
     * Remove a new Task to the local database
     */
    public boolean removeTask(Task task) {
        String[] whereArg = new String[]{mQualifierTitle, task.getId().toString()};
        return mDatabase.delete(TaskTable.NAME, whereClause, whereArg) != 0;

    }

    /**
     * Update a Task in the local database
     */
    public void updateTask(Task task) {
        String[] whereArg = new String[]{mQualifierTitle, task.getId().toString()};
        ContentValues values = getContentValues(task);
        mDatabase.update(TaskTable.NAME, values, whereClause, whereArg);
    }

    /**
     * Return a list of all qualifiers from the local database based on qualifier's name
     */
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        AllCursorWrapper cursor = this.queryTask(TaskTable.Cols.QUALIFIER_ID + "= ?",
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

    /**
     * Get a Task by UUID from local database
     */
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
        values.put(TaskTable.Cols.QUALIFIER_ID, mQualifierTitle);
        values.put(TaskTable.Cols.ID, task.getId().toString());
        values.put(TaskTable.Cols.TITLE, task.getTitle());
        values.put(TaskTable.Cols.COMPLETED, task.isCompleted());
        return values;
    }

    private AllCursorWrapper queryTask(String whereClause, String[] whereArgs) {

        return QueryMethods.queryDb(TaskTable.NAME, whereClause, whereArgs);
    }
}
