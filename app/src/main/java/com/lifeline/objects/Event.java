package com.lifeline.objects;

import java.util.Date;

public class Event {
    private String eventType;
    private Date date;

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
