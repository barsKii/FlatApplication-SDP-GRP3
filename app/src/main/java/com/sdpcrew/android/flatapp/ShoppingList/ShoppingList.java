package com.sdpcrew.android.flatapp.ShoppingList;

import java.util.ArrayList;

/**
 * Created by Shane Birdsall on 1/09/2016.
 *
 */
public class ShoppingList {
    private String listName;
    private ArrayList<String> list;

    public ShoppingList(String name) {
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
    private String getListName() {
        return listName;
    }
    public void addToList(String item) {
        if(item != null && !item.trim().isEmpty()) {
            list.add(item);
        }
    }
    public void deleteFromList(int index) {
        list.remove(index);
    }
    public void editListItem(int index, String newName) {
        list.set(index,newName);
    }
    public String toString() {
        return getListName();
    }
}
