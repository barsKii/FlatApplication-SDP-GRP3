package com.sdpcrew.android.flatapp.BillsManager;

import java.util.Date;
import java.util.UUID;
import com.sdpcrew.android.flatapp.*;

/**
 * Created by David on 20/09/2016.
 * Bill class is the model where a physical instance of a bill exists with appropriate accessor methods
 */
public class Bill {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mPaid;
    private String mAmount;

    public Bill() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        mPaid = paid;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }
}
