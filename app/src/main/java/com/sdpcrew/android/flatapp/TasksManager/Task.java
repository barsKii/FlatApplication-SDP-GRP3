package com.sdpcrew.android.flatapp.TasksManager;

import android.content.Context;
import android.content.res.Resources;

import com.sdpcrew.android.flatapp.R;

import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID mId;
    private String mTitle;
    private boolean mCompleted;

    public Task(UUID id) {
        mId = id;
        mTitle = Resources.getSystem().getString(R.string.no_title);
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
