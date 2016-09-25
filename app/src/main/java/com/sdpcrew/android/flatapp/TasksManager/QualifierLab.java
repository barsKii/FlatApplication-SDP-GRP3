package com.sdpcrew.android.flatapp.TasksManager;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class QualifierLab {
    private static QualifierLab sQualifierLab;
    private Context mContext;
    private List<Qualifier> mQualifiers;
//    private SQLiteDatabase mDatabase;

    public static QualifierLab get(Context context){
        if(sQualifierLab == null){
            sQualifierLab = new QualifierLab(context);
        }
        return sQualifierLab;
    }

    private void newQualifier(String name){
        Qualifier newQualifier = new Qualifier();
        newQualifier.setTitle(name);
        mQualifiers.add(newQualifier);
    }

    private QualifierLab(Context context){
        mQualifiers = new ArrayList<>();
        newQualifier("Weekly");
        newQualifier("Fortnightly");
        newQualifier("Monthly");
        newQualifier("BiMonthly");
        newQualifier("Trimonthly");
        newQualifier("Semester");
    }

    public void addQualifier(Qualifier c) {
        mQualifiers.add(c);
    }

    public boolean removeQualifier(Qualifier c){
        if(mQualifiers.contains(c)) {
            mQualifiers.remove(c);
            return true;
        }
        return false;
    }

    public List<Qualifier> getQualifiers(){
        return mQualifiers;
    }

    public Qualifier getQualifier(String name){

        for (Qualifier qualifier : mQualifiers) {
            if (qualifier.getTitle() .equals(name)) {
                return qualifier;
            }
        }
        return null;
    }
}
