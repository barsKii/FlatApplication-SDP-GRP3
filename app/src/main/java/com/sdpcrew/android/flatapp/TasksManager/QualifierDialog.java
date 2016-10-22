package com.sdpcrew.android.flatapp.TasksManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sdpcrew.android.flatapp.R;

import java.util.UUID;

/**
 * The QualifierDialog class is a DialogFragment used for creating and editing 'Qualifiers'.
 * Qualifiers store a roster (list of tasks), and the name of the roster.
 * QualifierDialog allows users to input text for the name of a Qualifier.
 */
public class QualifierDialog extends DialogFragment {

    private static final String ARG_QUALIFIER = "QualifierName";
    private static final String EXTRA_RETURN_DATA = "com.sdpcrew.android.flatapp.TasksManager.QualifierDialog";
    private EditText mEditTitle; // Editable text field for Qualifier name.
    private String mNewTitle; // Stores the name of the Qualifier once it is set/changed.
    private Qualifier mNewQualifier;

    public static QualifierDialog newInstance(UUID qualifierId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUALIFIER, qualifierId);

        // Create a new QualifierFragment and set the Qualifier Name based on 'qualifierName'.
        QualifierDialog fragment = new QualifierDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_qualifier, null);

        // Set up editable text field with a listener.
        mEditTitle = (EditText) v.findViewById(R.id.dialog_qualifier_settitle);
        mEditTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int j, int k) {
                // App currently does not use this function.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int j, int k) {
                mNewTitle = charSequence.toString(); // Assign user written text to variable.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // App currently does not use this function.
            }
        });

        /* If the stored argument is not null, then check for an existing Qualifier whose name
            matches the argument. */
        if (getArguments() != null) {
            UUID qualifierTitle = (UUID) getArguments().getSerializable(ARG_QUALIFIER);
            mNewQualifier = QualifierLab.get(getContext()).getQualifier(qualifierTitle);

            /*
                If a match exists (i.e the user is editing a Qualifier rather than creating one)
                then set the edit text field to contain the current name of the Qualifier.
              */
            if (mNewQualifier != null) {
                mEditTitle.setText(mNewQualifier.getTitle());
            }
        }

        /*
            Return an Alert dialog with an OK button which will be used to confirm the creation or
            change of a Qualifier.
          */
        return new AlertDialog.Builder(getActivity()).setView(v)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // If there is no qualifier, then create one
                                if (mNewQualifier == null) {
                                    /*
                                        If title is null or too short then no object is created.
                                        Limiting the length of Qualifier names was a design choice
                                        made to ensure all Qualifiers have meaningful titles.
                                      */
                                    if (mEditTitle == null || mEditTitle.length() <= 2) {
                                        // Display Toast message to inform user of their error.
                                        Toast.makeText(getActivity(),
                                                R.string.no_title_task_enter, Toast.LENGTH_LONG)
                                                .show();
                                    } else {
                                        // Create the Qualifier and add it to the list of Qualifiers.
                                        mNewQualifier = new Qualifier();
                                        mNewQualifier.setTitle(mNewTitle);
                                        if( QualifierLab.get(getContext()).containQualifier(mNewTitle)){
                                            Toast.makeText(getActivity(),
                                                    getString(R.string.rosrer_exists), Toast.LENGTH_LONG)
                                                    .show();
                                        }else{
                                            QualifierLab.get(getContext()).addQualifier(mNewQualifier);
                                        }
                                        sendResult(Activity.RESULT_OK); // Update View.
                                    }
                                } else { // The Qualifier already exists, so update it accordingly.
                                    mNewQualifier.setTitle(mNewTitle);
                                    QualifierLab.get(getContext()).updateQualifier(mNewQualifier);
                                    sendResult(Activity.RESULT_OK); // Update View.

                                }
                            }
                        })
                .create();
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
        intent.putExtra(EXTRA_RETURN_DATA, true);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}