package com.sdpcrew.android.flatapp.Database;

import com.sdpcrew.android.flatapp.ShoppingList.Item;
import com.sdpcrew.android.flatapp.ShoppingList.ShoppingList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shane on 24/10/2016.
 * This class handles the connection between the application and the Google Cloud MySQL database.
 * Functions provided include functionality to write and read data to/from the shopping lists.
 * This includes saving and reading the items within the lists. This functionality will be used
 * by a sync button in the main menu. A delete item function is also present. Further functions
 * which require access to the online database can be constructed within this class.
 */

public class MySqlConnection {
    private Connection conn;
    private Statement stmt;
    private String pass;
    private String user;
    private String url;

    public MySqlConnection() {
        pass = "flatapp123";
        user = "root";
        url = "jdbc:mysql://104.199.144.195:3306/flatappdb";
    }

    /**
        This function is used to delete a passed in item from the online database using SQL.
     */
    public void deleteItem(String listName, Item item) {
        try {
            if (conn == null) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
            }
            stmt = conn.createStatement();
            stmt.executeUpdate("delete from " + DbSchema.ShoppingItemsTable.NAME + " where item_title = '" + item.getItemName() + "' and list_id = '" + listName + "'");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
        This function is used to read in all of the shopping list data from the online server
        by using SQL statements.
     */
    public List<ShoppingList> readInShoppingLists() {
        try {
            if (conn == null) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
            }
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From shopping_lists");
            List<ShoppingList> shopping = new ArrayList<>();

            while (rs.next()) {
                shopping.add(new ShoppingList(rs.getString("list_title")));
            }
            rs = stmt.executeQuery("Select * From shopping_items");
            while (rs.next()) {
                for (ShoppingList s : shopping) {
                    if (s.getListName().equals(rs.getString("list_id"))) {
                        s.addItemToList(new Item(rs.getString("item_title")));
                    }
                }
            }

            stmt.close();
            return shopping;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
        This function is used to write all of the shopping list data to the online server
        by using SQL statements.
     */
    public void saveShoppingLists(List<ShoppingList> shopping) {
        try {
            if (conn == null) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
            }
            stmt = conn.createStatement();
            // insert ignore used to stop duplicate errors
            String listInsert = "insert ignore into " + DbSchema.ShoppingListsTable.NAME + " values";
            String itemInsert = "insert ignore into " + DbSchema.ShoppingItemsTable.NAME + " values";
            for (int i = 0; i < shopping.size(); i++) { // Add values to write
                List<Item> items = shopping.get(i).getListOfItems();
                for (int j = 0; j < items.size(); j++) {
                    itemInsert += "('" + shopping.get(i).getListName() + "', '" + items.get(j).getId() + "', '"
                            + items.get(j).getItemName() + "'),";
                }
                listInsert += "('" + shopping.get(i).getId() + "', '" + shopping.get(i).getListName() + "')";
                if (i != shopping.size() - 1) {
                    listInsert += ",";
                }
            }
            itemInsert = itemInsert.substring(0, itemInsert.length() - 1); // Remove extra ,
            stmt.executeUpdate(listInsert);
            stmt.executeUpdate(itemInsert); // Execute inserts.
            stmt.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void closeConnections() { // close database connection
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("SQL Exception thrown!");
                e.printStackTrace();
            }
        }
    }
}
