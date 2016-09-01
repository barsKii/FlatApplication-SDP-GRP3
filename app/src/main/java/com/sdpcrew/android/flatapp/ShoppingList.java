package com.sdpcrew.android.flatapp;

import java.util.ArrayList;

/**
 * Created by Shane Birdsall on 1/09/2016.
 */
public class ShoppingList {
    private String listName;
    private ArrayList<String> list;

    ShoppingList(String name) {
        setListName(name);
        list = new ArrayList<>();
    }

    public void setListName(String name) {
        if(name != null && !(name.trim().equals(""))) {
            listName = name;
        } else listName = "UNKNOWN";
    }
    public ArrayList<String> getList() {
        return list;
    }
    public String getListName() {
        return listName;
    }
    public void addToList(String item) {
        list.add(item);
    }
    public void deleteFromList(int index) {
        list.remove(index);
    }
    public String toString() {
        return getListName();
    }
}
