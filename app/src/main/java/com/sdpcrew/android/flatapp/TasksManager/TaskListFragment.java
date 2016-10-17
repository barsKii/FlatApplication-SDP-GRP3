package com.sdpcrew.android.flatapp.TasksManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sdpcrew.android.flatapp.R;

import java.util.List;
import java.util.UUID;

public class TaskListFragment extends Fragment {

    private static final String ARG_QUALIFIER_ID = "qualifierTitle";
    public static final String DIALOG_NEW_TASK = "NewTask";
    public static final int REQUEST_DATA = 0;
    private Qualifier mQualifier;
    private RecyclerView mTaskRecyclerView;
    private TaskAdapter mAdapter;
    private FloatingActionButton mAddButton;

    public static TaskListFragment newInstance(UUID qualifierId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUALIFIER_ID, qualifierId);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);//attach the arguments bundle to a fragment,
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        UUID qualifierId = (UUID) getArguments().getSerializable(ARG_QUALIFIER_ID);
        mQualifier = QualifierLab.get(getActivity()).getQualifier(qualifierId);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mQualifier == null) {
            container.removeAllViews();
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_recyclerview_qualifierlist, container, false);

        mTaskRecyclerView = (RecyclerView) view
                .findViewById(R.id.qualifierList_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fragment_task_add);
        mAddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mQualifier != null) {
                    FragmentManager manager = getFragmentManager();
                    TaskDialog dialog = TaskDialog.newInstance(mQualifier.getId());
                    dialog.setTargetFragment(TaskListFragment.this, REQUEST_DATA);
                    dialog.show(manager, DIALOG_NEW_TASK);
                    updateUI();
                } else {
                    Toast.makeText(getActivity(),
                            "Qualifier must be selected!", Toast.LENGTH_SHORT)
                            .show();
                }


            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATA) {
            updateUI();
        }
    }

    private void updateUI() {
        List<Task> tasks = mQualifier.getTaskLab().getTasks();
        mAdapter = new TaskAdapter(tasks);
        mTaskRecyclerView.setAdapter(mAdapter);
    }

    private class TaksHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView mTitleTextView;
        private CheckBox mCompleted;

        private Task mTask;

        public TaksHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.object_task_title);
            mCompleted = (CheckBox) itemView.findViewById(R.id.object_dialog_task_completed);
            mCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    mTask.setCompleted(isChecked);
                    mQualifier.getTaskLab().updateTask(mTask);
                }
            });

        }

        public void bindCrime(Task qualifier) {
            mTask = qualifier;
            mTitleTextView.setText(mTask.getTitle());
            mCompleted.setChecked(mTask.isCompleted());

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mTask.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();

            FragmentManager manager = getFragmentManager();
            TaskDialog dialog = TaskDialog.newInstance(mQualifier.getId(), mTask.getId());
            dialog.setTargetFragment(TaskListFragment.this, REQUEST_DATA);
            dialog.show(manager, DIALOG_NEW_TASK);
        }


        @Override
        public boolean onLongClick(View view) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Sure?")
                    .setMessage("Do you really want to delete ")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mQualifier.getTaskLab().removeTask(mTask);
                            updateUI();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
            return false;
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaksHolder> {

        private List<Task> mTasks;

        public TaskAdapter(List<Task> qualifiers) {
            mTasks = qualifiers;
        }

        @Override
        public TaksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_taskview, parent, false);
            return new TaksHolder(view);
        }

        @Override
        public void onBindViewHolder(TaksHolder holder, int position) {
            Task qualifier = mTasks.get(position);
            holder.bindCrime(qualifier);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }
}
