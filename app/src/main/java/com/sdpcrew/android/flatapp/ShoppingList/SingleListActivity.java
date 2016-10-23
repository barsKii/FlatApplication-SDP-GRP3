package com.sdpcrew.android.flatapp.ShoppingList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sdpcrew.android.flatapp.R;

import java.util.UUID;

import static com.sdpcrew.android.flatapp.ShoppingList.ShoppingListsActivity.EXTRA_SINGLE_LIST;

public class SingleListActivity extends AppCompatActivity {
    private ListView mListView;
    private FloatingActionButton mFab;
    private ShoppingList shoppingList;
    private TextView mAddText;
    private ImageView mAddImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        mAddText = (TextView) findViewById(R.id.addListText);
        mAddText.setText(getString(R.string.add_new_item));
        mAddImage = (ImageView) findViewById(R.id.addArrow);

        Intent intent = getIntent();
        UUID id;
        if (intent != null) {
            id = UUID.fromString(intent.getStringExtra(EXTRA_SINGLE_LIST));
            shoppingList = ShoppingListLab.get(getApplicationContext()).getShoppingList(id);
        }
        mListView = (ListView) findViewById(R.id.shoppingListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                final Item item = shoppingList.getItemByIndex(pos);

                LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
                View promptView = layoutInflater.inflate(R.layout.edit_item_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setView(promptView);

                final EditText editText = (EditText) promptView.findViewById(R.id.editText);
                editText.setText(item.getItemName());
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton(getString(R.string.change), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                item.setItemName("" + editText.getText());
                                shoppingList.updateItem(item);
                                updateListView();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setNeutralButton(getString(R.string.del_item),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        shoppingList.deleteItemFromList(item);
                                        updateListView();
                                    }
                                });
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });
        updateListView();

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
                        .setPositiveButton(getString(R.string.cap_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Item anItem = new Item("" + editText.getText());
                                shoppingList.addItemToList(anItem);
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
        });

    }

    public void updateListView() {
        if (shoppingList.getListOfItemsByName() != null) {
            mListView.setAdapter(new ArrayAdapter<>(this, R.layout.text_view,
                    shoppingList.getListOfItemsByName()));

            if (!shoppingList.getListOfItemsByName().isEmpty()) {
                mAddText.setVisibility(View.GONE);
                mAddImage.setVisibility(View.GONE);
            } else {
                mAddText.setVisibility(View.VISIBLE);
                mAddImage.setVisibility(View.VISIBLE);
            }
        }


    }
}
