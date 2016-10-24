package com.sdpcrew.android.flatapp.ShoppingList;
import java.util.UUID;

/**
 * Created by vini, Shane on 18/10/16.
 * Item class which represents a individual shopping list item.
 */

public class Item implements Comparable<Item> {

    private UUID mId;
    private String ItemName;

    public Item(String name) {
        this(UUID.randomUUID(), name);
    }

    public Item(UUID id, String name) {
        mId = id;
        setItemName(name);
    }

    public UUID getId() {
        return mId;
    }

    void setItemName(String name) {
        if (name != null && !(name.trim().equals(""))) {
            ItemName = name;
        } else ItemName = "???";
    }

    public String getItemName() {
        return ItemName;
    }

    @Override
    public int compareTo(Item o) {
        return getItemName().compareTo(o.getItemName());
    }

    @Override
    public boolean equals(Object o) {
        Item s = (Item) o;
        return getItemName().equals(s.getItemName());
    }
}
