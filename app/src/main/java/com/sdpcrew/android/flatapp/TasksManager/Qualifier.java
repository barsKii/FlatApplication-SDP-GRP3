package com.sdpcrew.android.flatapp.TasksManager;

public class Qualifier {

    private TaskLab tasks;

    private String mTitle;

    public Qualifier() {
        mTitle ="No name";
        tasks=new TaskLab();
    }

    public Qualifier(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public TaskLab getTaskLab(){
        return tasks;
    }

}
