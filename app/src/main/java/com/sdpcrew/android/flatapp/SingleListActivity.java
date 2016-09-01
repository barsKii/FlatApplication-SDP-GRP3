package com.sdpcrew.android.flatapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class SingleListActivity extends ListActivity {
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = getIntent().getExtras().getStringArrayList("list");
        setListAdapter(new ArrayAdapter(this, R.layout.activity_list, list));
    }
}
