package com.sdpcrew.android.flatapp.TasksManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.sdpcrew.android.flatapp.R;

import java.util.UUID;
/**
 * The TaskDialog class is a DialogFragment used for creating and editing 'Tasks'.
 * TaskDialog allows users to input or change data stored in a ask object when selected.
 * it works similarly to qualifierDialog
 */
public class TaskDialog extends DialogFragment {

    private static final String ARG_QUALIFIER = "Qualifier";
    private static final String ARG_TASK = "Task";
    private static final String EXTRA_Return_data = "new data";

    private EditText mTitle;// holds task's title
    private CheckBox mCompleted;// display if task has been completed
    private boolean mNoTaskPassed;// ensure dialog works when phone rotates

    private TaskLab mTaskLab;// store a reference for the Task lab class
    private Task mNewTask;// holds a new task or reference to an existing tasks(passed as argument)

    /**
     * used to return instance of this dialog and also stores an argument (id of the qualifier)
     */
    public static TaskDialog newInstance(UUID qualifierId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUALIFIER, qualifierId);
        TaskDialog fragment = new TaskDialog();
        fragment.setArguments(args);
        return fragment;
    }

    /**used to return instance of this dialog and also stores arguments (id of the qualifier and existent task id)
     * this is used when a task is selected
     */
    public static TaskDialog newInstance(UUID qualifierId, UUID TaskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUALIFIER, qualifierId);
        args.putSerializable(ARG_TASK, TaskId);
        TaskDialog fragment = new TaskDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_task, null);
        mTitle = (EditText) v.findViewById(R.id.dialog_task_settitle);
        mCompleted = (CheckBox) v.findViewById(R.id.fragment_dialog_task_completed);

        UUID qualifierName = (UUID) getArguments().getSerializable(ARG_QUALIFIER);
        mTaskLab = QualifierLab.get(getContext()).getQualifier(qualifierName).getTaskLab();
        mNoTaskPassed = getArguments().getSerializable(ARG_TASK) != null;
        if (mNoTaskPassed) {
            UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK);

            mNewTask = mTaskLab.getTask(taskId);
            mTitle.setText(mNewTask.getTitle());
            mCompleted.setChecked(mNewTask.isCompleted());

        } else {
            mNewTask = new Task();
        }


        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewTask.setTitle(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mNewTask.setCompleted(isChecked);
            }
        });


        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(v)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mTitle == null || mTitle.length() <= 2) {
                                    Toast.makeText(getActivity(),
                                            R.string.no_title_qualifier_enter, Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    if (!mNoTaskPassed) {
                                        mTaskLab.addTask(mNewTask);
                                    } else {
                                        mTaskLab.updateTask(mNewTask);
                                    }
                                    sendResult(Activity.RESULT_OK);
                                }

                            }
                        })
                .create();
        return dialog;
    }

    /**
     * This method is used when a Qualifier is created or edited due to the View needing to be
     * updated.
     * Androids onActivityResult method is called manually to detect a forced alteration and
     * therefore trigger an update.
     */
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_Return_data, true);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
