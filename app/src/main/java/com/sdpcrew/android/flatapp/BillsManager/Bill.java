package com.sdpcrew.android.flatapp.BillsManager;

import java.util.Date;
import java.util.UUID;

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
    private String mDescription;

    public Bill(UUID id) {
        mId = id;
        mDate = new Date();
    }

    Bill() {
        this(UUID.randomUUID());
    }

    boolean isPaid() {
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

    String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
