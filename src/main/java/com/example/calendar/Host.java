package com.example.calendar.model;

import java.util.List;

public class Host {
    private String name;
    private List<Event> events;

    public Host(String name, List<Event> events) {
        this.name = name;
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
