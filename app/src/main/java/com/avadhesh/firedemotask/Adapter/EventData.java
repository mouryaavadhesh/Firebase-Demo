package com.avadhesh.firedemotask.Adapter;

import java.io.Serializable;
import java.util.Date;

public class EventData implements Serializable {
    public String id;
    public String title;
    public String description;
    public long date;
    public Date dateTime;
    public String eventDate,participants;


    public EventData() {

    }
    public EventData(String title, String description,  long date, Date dateTime,String eventDate,String participants
    ) {

        this.title = title;
        this.description = description;
        this.date = date;
        this.dateTime = dateTime;
        this.eventDate=eventDate;
        this.participants=participants;
    }
    public EventData(String id, String title, String description,  long date, Date dateTime,String eventDate,String participants
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.dateTime = dateTime;
        this.eventDate=eventDate;
        this.participants=participants;


    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public long getDate() {
        return date;
    }


    public void setDate(long date) {
        this.date = date;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
