package com.sdpcrew.android.flatapp.TasksManager;

public class Task {

    private String mTitle;
    private boolean mCompleted;

    public Task() {
        mTitle ="No Title";
        mCompleted = false;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
