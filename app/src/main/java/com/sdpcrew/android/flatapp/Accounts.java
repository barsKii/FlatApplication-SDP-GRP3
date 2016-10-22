package com.sdpcrew.android.flatapp;

import java.util.List;

public class Accounts {

    List<String> Accounts;

    public Accounts() {
        Accounts.add("Vini");
        Accounts.add("David");
        Accounts.add("Shane");
        Accounts.add("Nikhil");
    }

    public List<String> getAccounts() {
        return Accounts;
    }

    public void setAccounts(List<String> accounts) {
        Accounts = accounts;
    }
}
