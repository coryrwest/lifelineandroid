package com.lifeline.objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Step {
    public int count;
    private String date;
    public boolean isNew = false;
    public long id;

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        this.date = dateFormat.format(date);
    }

    public void setDate(String date) {
        this.date = date;
    }
}
