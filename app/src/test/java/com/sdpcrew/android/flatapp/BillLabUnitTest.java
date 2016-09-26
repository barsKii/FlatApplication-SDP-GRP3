package com.sdpcrew.android.flatapp;

import android.text.TextUtils;

import com.sdpcrew.android.flatapp.BillsManager.Bill;
import com.sdpcrew.android.flatapp.BillsManager.BillLab;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

public class BillLabUnitTest {
    BillLab lab;

    @Before
    public void setUp() {
        lab = BillLab.get(null);
        lab.refreshLab();
    }


    @Test //Can add a single bill
    public void testAddBill() {
        Bill bill = new Bill();
        lab.addBill(bill);
        Assert.assertTrue(1 == lab.getBills().size());
    }

    @Test //Can add multiple bills
    public void testAddMultipleBills() {
        Bill b1 = new Bill();
        Bill b2 = new Bill();
        Bill b3 = new Bill();
        lab.addBill(b1);
        lab.addBill(b2);
        lab.addBill(b3);
        Assert.assertTrue(3 == lab.getBills().size());
    }

    //Removing bill checks only last bill added as clients can only add 1 bill at a time
    @Test //Removes last bill added if title is empty
    public void noTitle() {
        Bill emptyBill = new Bill();
        Bill content = new Bill();
        content.setAmount("50");
        content.setTitle("Title");
        lab.addBill(content);
        lab.rejectIncompleteBill();
        lab.addBill(emptyBill);
        lab.rejectIncompleteBill();
        boolean test1 = 1 == lab.getBills().size();
        boolean test2 = content.getId().equals(lab.getBill(content.getId()).getId());

        Assert.assertTrue(test1 && test2);
    }

    @Test //Removes last bill added if amount is empty, even with title
    public void noAmount() {
        Bill emptyBill = new Bill();
        Bill content = new Bill();
        content.setAmount("50");
        content.setTitle("Title");
        emptyBill.setTitle("Title");
        lab.addBill(content);
        lab.rejectIncompleteBill();
        lab.addBill(emptyBill);
        lab.rejectIncompleteBill();
        boolean test1 = 1 == lab.getBills().size();
        boolean test2 = content.getId().equals(lab.getBill(content.getId()).getId());

        Assert.assertTrue(test1 && test2);
    }

    @Test //Adds a bill when both title and amount are present
    public void twoBillsPassed() {
        Bill Bill = new Bill();
        Bill Bill2 = new Bill();
        Bill.setAmount("50");
        Bill.setTitle("Title");
        Bill2.setTitle("Blah");
        Bill2.setAmount("75");
        lab.addBill(Bill);
        lab.rejectIncompleteBill();
        lab.addBill(Bill2);
        lab.rejectIncompleteBill();

        Assert.assertTrue(2 == lab.getBills().size());
    }

    @Test //Edit a existing bills title and amount
    public void editBill() {
        Bill bill = new Bill();
        bill.setTitle("Original");
        bill.setAmount("1");
        boolean test1 = (bill.getTitle().matches("Original") && bill.getAmount().matches("1"));
        bill.setTitle("New");
        bill.setAmount("100");
        boolean test2 = (bill.getTitle().matches("New") && bill.getAmount().matches("100"));
        Assert.assertTrue(test1 && test2);
    }


}
