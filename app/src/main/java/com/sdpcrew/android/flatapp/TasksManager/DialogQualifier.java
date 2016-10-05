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
import android.widget.EditText;
import android.widget.Toast;

import com.sdpcrew.android.flatapp.R;

/**
 * This is a DialogFragment used as a pop dialog which holds for now
 * a title field only. It holds a method newInstance were stores an argument. It also
 * send information back to the caller class when info is updated.
 * This fragment has two functions, either create a new Qualifier or alter a qualifier.
 * Qualifier is a list which holds several tasks
 */
public class DialogQualifier extends DialogFragment {

    //Variables
    private EditText mTitle;
    private String mNewTitle;
    private Qualifier mNewQualifier;
    //Constants
    private static final String ARG_QUALIFIER = "QualifierName";
    private static final String EXTRA_RETURN_DATA = "com.sdpcrew.android.flatapp.TasksManager.DialogQualifier";

    //return this fragment at sametime stores an argument to this
    public static DialogQualifier newInstance(String qualifier ) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUALIFIER, qualifier);
        DialogQualifier fragment = new DialogQualifier();
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_qualifier, null);
        //controls editable text fild (Title)
        mTitle = (EditText) v.findViewById(R.id.dialog_qualifier_settitle);
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewTitle = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //Gets argument stored, it will check for a existent qulifier with same name as the argument
        if(getArguments() != null) {
            String qualifierTitle = (String) getArguments().getSerializable(ARG_QUALIFIER);
            if (QualifierLab.get(getContext()).getQualifier(qualifierTitle) != null) {
                mNewQualifier = QualifierLab.get(getContext()).getQualifier(qualifierTitle);
                mTitle.setText(mNewQualifier.getTitle());
            }
        }

        //Return a Alert diaglog with an OK button which will determine if new list should be created or updated
        return new AlertDialog.Builder(getActivity()).setView(v)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //if there is no qualifier creates one
                                if (mNewQualifier == null) {
                                    //if title is null or too short no object is created, mits a toast
                                    if (mTitle == null || mTitle.length() <= 2) {
                                        Toast.makeText(getActivity(),
                                                R.string.no_title_task_enter, Toast.LENGTH_LONG)
                                                .show();
                                    } else {
                                        mNewQualifier = new Qualifier();
                                        mNewQualifier.setTitle(mNewTitle);
                                        QualifierLab.get(getContext()).addQualifier(mNewQualifier);
                                        sendResult(Activity.RESULT_OK);//sends result
                                    }
                                }else{
                                    mNewQualifier.setTitle(mNewTitle);
                                    sendResult(Activity.RESULT_OK);//sends result
                                }
                            }
                        })
                .create();

    }

    /**
     * This method put an extra which should be detected on the caller fragment.
     * this method manually calls the onActivityResult method of the caller fragment so it can detect
     * alteration and therefore update view.
     * @param resultCode
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

