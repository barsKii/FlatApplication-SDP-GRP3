package com.sdpcrew.android.flatapp.Database;

import android.os.AsyncTask;
import android.util.Log;

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
 */

public class MySqlConnection extends AsyncTask<Void, Void, List<ShoppingList>> {
    private Connection conn;
    private Statement stmt;
    private String pass = "flatapp123";
    private String user = "root";
    private String url = "jdbc:mysql://104.199.144.195:3306/flatappdb";

    public MySqlConnection() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection(url, user, pass);
//        } catch (SQLException e) {
//            Log.e("error", "error", e);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected List<ShoppingList> doInBackground(Void... params) {

        return readInShoppingLists();
    }


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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveShoppingLists(List<ShoppingList> shopping) {
        try {
            if (conn == null) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
            }
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM " + DbSchema.ShoppingListsTable.NAME);
            stmt.executeUpdate("DELETE FROM " + DbSchema.ShoppingItemsTable.NAME);
            String listInsert = "insert into " + DbSchema.ShoppingListsTable.NAME + " values";
            String itemInsert = "insert into " + DbSchema.ShoppingItemsTable.NAME + " values";
            for (int i = 0; i < shopping.size(); i++) {
                List<Item> items = shopping.get(i).getListOfItems();
                for (Item item : items) {
                    itemInsert += "('" + shopping.get(i).getListName() + "', " + item.getId() + "', "
                            + item.getId() + ")";
                    if (!item.equals(items.get(items.size()))) {
                        itemInsert += ",";
                    }
                }
                listInsert += "('" + shopping.get(i).getId() + "', " + shopping.get(i).getListName() + ")";
                if (i != shopping.size() - 1) {
                    listInsert += ",";
                }
            }
            stmt.executeUpdate(listInsert);
            stmt.executeUpdate(itemInsert);
            stmt.close();
        } catch (SQLException e) {
            Log.e("error", "error", e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
