package com.sdpcrew.android.flatapp.TasksManager;

import java.util.ArrayList;
import java.util.List;

public class TaskLab {

    private List<Task> mTaks;
//    private SQLiteDatabase mDatabase;

    public TaskLab(){
        mTaks = new ArrayList<>();

        Task test1 = new Task();
        test1.setTitle("test1");
        mTaks.add(test1);

        Task test2 = new Task();
        test2.setTitle("test2");
        mTaks.add(test2);

        Task test3 = new Task();
        test3.setTitle("test3");
        mTaks.add(test3);
    }

    public void addTask(Task c) {
        mTaks.add(c);
    }

    public boolean removeTask(Task c){
       return mTaks.remove(c);

    }

    public List<Task> getTaks(){
        return mTaks;
    }

    public Task getTask(String name){

        for (Task task : mTaks) {
            if (task.getTitle() .equals(name)) {
                return task;
            }
        }
        return null;
    }
}
