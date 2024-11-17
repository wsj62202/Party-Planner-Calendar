package com.example.calendar.model;

public class Guest {
    private String name;
    private String email;
    private String role;
    private boolean rsvp;

    public Guest(String name, String email, String role, boolean rsvp) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.rsvp = rsvp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isRsvp() {
        return rsvp;
    }

    public void setRsvp(boolean rsvp) {
        this.rsvp = rsvp;
    }
}
