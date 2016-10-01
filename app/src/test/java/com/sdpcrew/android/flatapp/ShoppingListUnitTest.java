package com.sdpcrew.android.flatapp;

import com.sdpcrew.android.flatapp.ShoppingList.ShoppingList;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Shane on 1/09/2016.
 * Unit Test Class for ShoppingList Class.
 */

public class ShoppingListUnitTest {
    private ShoppingList shoppingList;

    @Test
    public void testShoppingList() {

        Assert.assertNull(shoppingList);

        shoppingList = new ShoppingList("");
        Assert.assertNotNull(shoppingList);

        Assert.assertEquals("UNKNOWN", shoppingList.toString());
        Assert.assertTrue(shoppingList.getList().isEmpty());

        shoppingList.setListName(null);
        Assert.assertEquals("UNKNOWN", shoppingList.toString());

        shoppingList.setListName("Test List");
        Assert.assertEquals("Test List", shoppingList.toString());

        shoppingList.addToList("Test item");
        shoppingList.addToList(null);
        Assert.assertFalse(shoppingList.getList().isEmpty());
        Assert.assertTrue(shoppingList.getList().size() == 1);
        Assert.assertEquals("Test item", shoppingList.getList().get(0));

        shoppingList.editListItem(0, "edit test");
        Assert.assertEquals("edit test", shoppingList.getList().get(0));

        shoppingList.deleteFromList(0);
        Assert.assertTrue(shoppingList.getList().isEmpty());
    }
}
