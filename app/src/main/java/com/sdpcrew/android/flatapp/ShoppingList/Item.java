package com.sdpcrew.android.flatapp.ShoppingList;

import java.util.UUID;

/**
 * Created by vini on 18/10/16.
 */

public class Item {

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

    public void setItemName(String name) {
        if (name != null && !(name.trim().equals(""))) {
            ItemName = name;
        } else ItemName = "UNKNOWN";
    }

    public String getItemName() {
        return ItemName;
    }

}
