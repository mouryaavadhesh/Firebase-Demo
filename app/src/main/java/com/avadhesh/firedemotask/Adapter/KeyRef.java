package com.avadhesh.firedemotask.Adapter;

import java.util.Date;

public class KeyRef {

    public String key;
    public Date dateTime;

    public KeyRef(String key, Date dateTime) {
        this.key = key;
        this.dateTime = dateTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
