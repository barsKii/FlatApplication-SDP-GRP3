package com.sdpcrew.android.flatapp.ShoppingList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.sdpcrew.android.flatapp.R;

import java.util.List;

public class ShoppingListsActivity extends AppCompatActivity {
    //    final static ArrayList lists = (ArrayList) ShoppingListLab.get().getShoppingLists();
    public static final String EXTRA_SINGLE_LIST = "openlist";
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        mListView = (ListView) findViewById(R.id.shoppingListView);

        if (mListView != null) {
            updateListView();

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int i, long l) {
                    Intent in = new Intent(v.getContext(), SingleListActivity.class);
                    in.putExtra(EXTRA_SINGLE_LIST,
                            ShoppingListLab.get(getApplicationContext()).getShoppingLists().get(i).getId().toString());
                    startActivity(in);
                }
            });
        }
    }

    public void createNewShoppingList(View v) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.new_list_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.editText);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(getString(R.string.cap_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ShoppingListLab.get(getApplicationContext()).addShoppingList(new ShoppingList("" + editText.getText()));
                        updateListView();
                    }
                })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void updateListView() {
        List<String> list = ShoppingListLab.get(getApplicationContext()).getShoppingListsNames();
        if (list != null) {
            mListView.setAdapter(new ArrayAdapter<>(this, R.layout.text_view, list));
        }
    }

}
