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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sdpcrew.android.flatapp.R;

import java.util.List;

public class QualifierListFragment extends Fragment {
    private static final String SAVED_QUALIFIER_SELECTED ="GetQualiifier" ;
    private static final String SAVED_mAddButton = "SAVED_button_condition";
    private static final String DIALOG_QUALIFIER ="NewQualifier";
    private static final int REQUEST_DATA = 0;//Be aware same Tag is being used by both Dialogs


    private FloatingActionButton mAddButton;
    private RecyclerView mQualifierRecyclerView;
    private QualifierAdapter mAdapter;
    private Qualifier mQualifierSelected;


    private Callbacks mCallbacks;
    public interface Callbacks {
        void onQualifierSelected(Qualifier crime);
        void onQualifierDelete();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_qualifier_view, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_qualifier:
                FragmentManager manager = getFragmentManager();
                DialogQualifier dialog = new DialogQualifier();
                dialog.setTargetFragment(QualifierListFragment.this, REQUEST_DATA);
                dialog.show(manager, DIALOG_QUALIFIER);
                return true;

            case R.id.menu_item_delete:
                if (mQualifierSelected == null) {
                    Toast.makeText(getActivity(),
                            R.string.menu_no_qualifier_selected, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Sure?")
                            .setMessage("Do you really want to delete ")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    QualifierLab.get(getContext()).removeQualifier(mQualifierSelected);
                                    mAddButton.setVisibility(View.GONE);
                                    mQualifierSelected = null;
                                    mCallbacks.onQualifierDelete();
                                    updateUI();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_qualifierlist, container, false);

        mQualifierRecyclerView = (RecyclerView) view
                .findViewById(R.id.qualifierList_recycler_view);
        mQualifierRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fragment_task_add);
        mAddButton.setVisibility(View.GONE);

            if (savedInstanceState != null) {
                if(savedInstanceState.getBoolean(SAVED_mAddButton)){
                    mAddButton.setVisibility(View.VISIBLE);
                }
                if(savedInstanceState.get(SAVED_QUALIFIER_SELECTED) != null){
                    mQualifierSelected = QualifierLab.get(getContext()).
                            getQualifier((String) savedInstanceState.get(SAVED_QUALIFIER_SELECTED));
                }
            }

        updateUI();

        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle onSavedInstanceState) {
        super.onSaveInstanceState(onSavedInstanceState);
        if(mAddButton.getVisibility() == View.VISIBLE){
            onSavedInstanceState.putBoolean(SAVED_mAddButton,true);
            updateUI();
        }
        if(mQualifierSelected != null) {
            onSavedInstanceState.putString(SAVED_QUALIFIER_SELECTED, mQualifierSelected.getTitle());
        }
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
        QualifierLab qualifier = QualifierLab.get(getActivity());
        List<Qualifier> qualifiers = qualifier.getQualifiers();

        mAdapter = new QualifierAdapter(qualifiers);
        mQualifierRecyclerView.setAdapter(mAdapter);
    }

    private class QualifierHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        private TextView mTitleTextView;
        private Qualifier mQualifier;

        public QualifierHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.object_qualifier_title);

        }

        public void bindCrime(Qualifier qualifier) {
            mQualifier = qualifier;
            mTitleTextView.setText(mQualifier.getTitle());

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mQualifier.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
            if(mAddButton != null){
                if(mAddButton.getVisibility() == View.GONE) {
                    mAddButton.setVisibility(View.VISIBLE);
                }
            }
           mCallbacks.onQualifierSelected(mQualifier);
           mQualifierSelected = mQualifier;
        }

        @Override
        public boolean onLongClick(View view) {

            FragmentManager manager = getFragmentManager();
            DialogQualifier dialog = DialogQualifier.newInstance(mQualifier.getTitle());
            dialog.setTargetFragment(QualifierListFragment.this, REQUEST_DATA);
            dialog.show(manager, DIALOG_QUALIFIER);

            return false;
        }

    }

    private class QualifierAdapter extends RecyclerView.Adapter<QualifierHolder> {

        private List<Qualifier> mQualifiers;

        public QualifierAdapter(List<Qualifier> qualifiers) {
            mQualifiers = qualifiers;
        }

        @Override
        public QualifierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_qualifierview, parent, false);
            return new QualifierHolder(view);
        }

        @Override
        public void onBindViewHolder(QualifierHolder holder, int position) {
            Qualifier qualifier = mQualifiers.get(position);
            holder.bindCrime(qualifier);
        }

        @Override
        public int getItemCount() {
            return mQualifiers.size();
        }
    }
}
