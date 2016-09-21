package com.sdpcrew.android.flatapp.ShoppingList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.sdpcrew.android.flatapp.R;

public class SingleListActivity extends AppCompatActivity {
    ShoppingList list;
    int listNum;
    private ListView mListView;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        mListView = (ListView) findViewById(R.id.shoppingListView);
        listNum = getIntent().getExtras().getInt("list");
        list = ShoppingListsActivity.lists.get(listNum);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
                View promptView = layoutInflater.inflate(R.layout.new_list_item_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setView(promptView);

                final EditText editText = (EditText) promptView.findViewById(R.id.editText);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ShoppingListsActivity.lists.get(listNum);
                                list.addToList("" + editText.getText());
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                mListView.setAdapter(new ArrayAdapter<>(v.getContext(), R.layout.text_view, list.getList()));
            }
        });
        if (list != null) {
            mListView.setAdapter(new ArrayAdapter<>(this, R.layout.text_view, list.getList()));
        }
    }
}
