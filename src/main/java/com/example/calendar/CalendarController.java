package com.example.calendar.controller;

import com.example.calendar.model.Calendar;
import com.example.calendar.model.Event;
import com.example.calendar.model.Guest;
import com.example.calendar.model.Host;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CalendarController {

    private final List<Host> hosts = new ArrayList<>();

    public CalendarController() {
        // Sample Data (optional for testing)
        Host sampleHost = new Host("Alice", new ArrayList<>());
        Event sampleEvent = new Event(
            "Meeting",
            "2024-11-20",
            "10:00",
            "Conference Room",
            "Work",
            new ArrayList<>()
        );
        sampleHost.getEvents().add(sampleEvent);
        hosts.add(sampleHost);
    }

    @GetMapping("/hosts")
    public List<Host> getAllHosts() {
        return hosts;
    }

    @PostMapping("/hosts/{hostName}/events")
    public String createEvent(@PathVariable String hostName, @RequestBody Event newEvent) {
        Optional<Host> host = hosts.stream().filter(h -> h.getName().equalsIgnoreCase(hostName)).findFirst();

        if (host.isPresent()) {
            host.get().getEvents().add(newEvent);
            return "Event created successfully!";
        } else {
            Host newHost = new Host(hostName, new ArrayList<>());
            newHost.getEvents().add(newEvent);
            hosts.add(newHost);
            return "New host created and event added successfully!";
        }
    }

    @PostMapping("/hosts/{hostName}/events/{eventName}/guests")
    public String addGuestToEvent(
        @PathVariable String hostName,
        @PathVariable String eventName,
        @RequestBody Guest guest
    ) {
        Optional<Host> host = hosts.stream().filter(h -> h.getName().equalsIgnoreCase(hostName)).findFirst();

        if (host.isPresent()) {
            Optional<Event> event = host.get().getEvents().stream()
                .filter(e -> e.getTitle().equalsIgnoreCase(eventName))
                .findFirst();

            if (event.isPresent()) {
                event.get().getGuests().add(guest);
                return "Guest added to the event successfully!";
            } else {
                return "Event not found.";
            }
        } else {
            return "Host not found.";
        }
    }
}
