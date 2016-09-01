package com.sdpcrew.android.flatapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShoppingListsActivity extends ListActivity {
    final ArrayList<ShoppingList> lists = new ArrayList<>();
    private Button mNewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShoppingList shoppingTest1 = new ShoppingList("Tuesday shopping list");
        ShoppingList shoppingTest2 = new ShoppingList("New world shopping");
        ShoppingList shoppingTest3 = new ShoppingList("Next weeks shop");
        shoppingTest1.addToList("Milk");
        shoppingTest1.addToList("Bread");
        shoppingTest1.addToList("Butter");
        shoppingTest1.addToList("Bacon");
        shoppingTest2.addToList("Eggs");
        shoppingTest3.addToList("Rice");
        lists.add(shoppingTest1);
        lists.add(shoppingTest2);
        lists.add(shoppingTest3);

        setListAdapter(new ArrayAdapter(this, R.layout.activity_shopping_list, lists));

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int i, long l) {
                Intent in = new Intent(v.getContext(), SingleListActivity.class);
                in.putExtra("list", lists.get(i).getList());
                startActivity(in);
            }
        });

        //setListAdapter(new ArrayAdapter(this, R.layout.activity_shopping_list, lists));

    }
}
