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
        Assert.assertTrue(shoppingList.getListOfItems().isEmpty());

        shoppingList.setListName(null);
        Assert.assertEquals("UNKNOWN", shoppingList.toString());

        shoppingList.setListName("Test List");
        Assert.assertEquals("Test List", shoppingList.toString());

        shoppingList.addItemToList("Test item");
        shoppingList.addItemToList(null);
        Assert.assertFalse(shoppingList.getListOfItems().isEmpty());
        Assert.assertTrue(shoppingList.getListOfItems().size() == 1);
        Assert.assertEquals("Test item", shoppingList.getListOfItems().get(0));

        shoppingList.updateItem(0, "edit test");
        Assert.assertEquals("edit test", shoppingList.getListOfItems().get(0));

        shoppingList.deleteItemFromList(0);
        Assert.assertTrue(shoppingList.getListOfItems().isEmpty());
    }
}
