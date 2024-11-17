package com.example.calendar.model;

import java.util.List;

public class Event {
    private String title;
    private String date;
    private String time;
    private String location;
    private String type;
    private List<Guest> guests;

    public Event(String title, String date, String time, String location, String type, List<Guest> guests) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.type = type;
        this.guests = guests;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }
}
