package com.sdpcrew.android.flatapp.TasksManager;

import android.content.res.Resources;

import com.sdpcrew.android.flatapp.R;

import java.util.UUID;

/**Class stores data of a qualifier(Roster). Each instance of this class has a UUID which is assigned
 * to it when created.
 */
public class Qualifier {

    private UUID mId;
    private String mTitle;

    Qualifier() {
        this(UUID.randomUUID());
    }

    public Qualifier(UUID id) {
        mId = id;
        mTitle = Resources.getSystem().getString(R.string.no_title);
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

    TaskLab getTaskLab() {

        return new TaskLab(mTitle);
    }

}
