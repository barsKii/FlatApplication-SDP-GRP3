package com.sdpcrew.android.flatapp.TasksManager;

import android.support.v4.app.Fragment;

import com.sdpcrew.android.flatapp.R;

/**
 * TaskManagerActivity is the main activity of task Manager, each controls and manipulate different fragments
 * It implements QualifierListFragment.Callbacks ensure updates.
 */
public class TaskManagerActivity extends SingleFragmentActivity implements QualifierListFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new QualifierListFragment();
    }


    @Override
    public void onQualifierSelected(Qualifier qualifier) {
        Fragment newDetail = TaskListFragment.newInstance(qualifier.getId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_tasks, newDetail)
                .commit();
    }

    @Override
    public void onQualifierDelete() {
        TaskListFragment taskList = (TaskListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container_tasks);
        this.getSupportFragmentManager().beginTransaction().remove(taskList).commit();
    }


}
