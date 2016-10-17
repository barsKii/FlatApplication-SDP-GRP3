package com.sdpcrew.android.flatapp.TasksManager;

import java.util.UUID;

public class Qualifier {

    private UUID mId;
    private String mTitle;

    public Qualifier() {
        this(UUID.randomUUID());
    }

    public Qualifier(UUID id) {
        mId = id;
        mTitle = "No Title";
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public TaskLab getTaskLab() {

        return new TaskLab(mTitle);
    }

}
