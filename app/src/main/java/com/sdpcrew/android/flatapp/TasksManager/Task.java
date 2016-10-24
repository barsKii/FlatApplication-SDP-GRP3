package com.sdpcrew.android.flatapp.TasksManager;

import java.util.UUID;

/**
 * Task is responsible to store all data related to a task
 */
public class Task {
    private UUID mId;
    private String mTitle;
    private boolean mCompleted;

    public Task(UUID id) {
        mId = id;
        mTitle = "";
        mCompleted = false;
    }

    Task() {
        this(UUID.randomUUID());
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

    boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
