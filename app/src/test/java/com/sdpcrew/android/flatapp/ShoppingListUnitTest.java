package com.sdpcrew.android.flatapp;

import com.sdpcrew.android.flatapp.ShoppingList.Item;
import com.sdpcrew.android.flatapp.ShoppingList.ShoppingList;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Shane on 1/09/2016.
 * Unit Test Class for ShoppingList Class.
 */

public class ShoppingListUnitTest {
    // TODO: Fix this test class. (not working after vini made changes)
    private ShoppingList shoppingList;

    @Test
    public void testShoppingList() {

        Assert.assertNull(shoppingList);

        shoppingList = new ShoppingList("List");
        Assert.assertNotNull(shoppingList);

        Assert.assertEquals("List", shoppingList.toString());
        Assert.assertTrue(shoppingList.getListOfItems().isEmpty());

        shoppingList.setListName(null);
        Assert.assertEquals("UNKNOWN", shoppingList.toString());

        shoppingList.setListName("Test List");
        Assert.assertEquals("Test List", shoppingList.toString());


        Item test = new Item("Test item");
        shoppingList.addItemToList(test);
        shoppingList.addItemToList(null);
        Assert.assertFalse(shoppingList.getListOfItems().isEmpty());
        Assert.assertTrue(shoppingList.getListOfItems().size() == 1);
        Assert.assertEquals(test, shoppingList.getListOfItems().get(0));

        test = new Item("edit test");
        shoppingList.updateItem(test);
        Assert.assertEquals(test, shoppingList.getListOfItems().get(0));

        shoppingList.deleteItemFromList(test);
        Assert.assertTrue(shoppingList.getListOfItems().isEmpty());
    }
}
