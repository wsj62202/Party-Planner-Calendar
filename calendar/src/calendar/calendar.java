package calendar;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class calendar {

    // Event class to represent an event with details
    static class Event implements Comparable<Event> {
        private String title;
        private LocalDate date;
        private LocalTime time;
        private String location;
        private String type;
        private List<Guest> guests;

        public Event(String title, LocalDate date, LocalTime time, String location, String type) {
            this.title = title;
            this.date = date;
            this.time = time;
            this.location = location;
            this.type = type;
            this.guests = new ArrayList<>();
        }

        public String getTitle() { return title; }
        public LocalDate getDate() { return date; }
        public LocalTime getTime() { return time; }
        public String getLocation() { return location; }
        public String getType() { return type; }
        public List<Guest> getGuests() { return guests; }

        // Add a guest to the event
        public void addGuest(Guest guest) {
            guests.add(guest);
        }

        // Sort events by date and time
        @Override
        public int compareTo(Event other) {
            if (!this.date.equals(other.date)) {
                return this.date.compareTo(other.date);
            }
            return this.time.compareTo(other.time);
        }

        @Override
        public String toString() {
            return "Title: " + title + ", Date: " + date + ", Time: " + time +
                   ", Location: " + location + ", Type: " + type;
        }
    }

    // Guest class to represent an invitee with RSVP status
    static class Guest {
        private String name;
        private String email;
        private String role;
        private boolean rsvp;

        public Guest(String name, String email, String role) {
            this.name = name;
            this.email = email;
            this.role = role;
            this.rsvp = false;
        }

        public void setRsvp(boolean rsvp) {
            this.rsvp = rsvp;
        }

        public boolean getRsvp() { return rsvp; }

        @Override
        public String toString() {
            return "Guest: " + name + ", Email: " + email + ", Role: " + role + ", RSVP: " + (rsvp ? "Yes" : "No");
        }
    }

    // Host class to manage a list of events
    static class Host {
        private String name;
        private List<Event> events;

        public Host(String name) {
            this.name = name;
            this.events = new ArrayList<>();
        }

        public String getName() { return name; }
        public List<Event> getEvents() { return events; }

        // Add an event to the host's list of events and sort the list
        public void addEvent(Event event) {
            events.add(event);
            Collections.sort(events);
        }
    }

    // Admin class to manage all hosts and events
    static class Admin {
        private List<Host> hosts;

        public Admin() {
            this.hosts = new ArrayList<>();
        }

        public void addHost(Host host) {
            hosts.add(host);
        }

        public void viewAllHosts() {
            if (hosts.isEmpty()) {
                System.out.println("No hosts available.");
            } else {
                for (Host host : hosts) {
                    System.out.println("Host: " + host.getName());
                    for (Event event : host.getEvents()) {
                        System.out.println("  - " + event);
                    }
                }
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static Admin admin = new Admin();

    public static void main(String[] args) {
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("Welcome! Are you a host, admin, or would you like to exit? (Enter 'host', 'admin', or 'exit')");
            String userRole = scanner.nextLine().trim().toLowerCase();

            switch (userRole) {
                case "host" -> handleHost();
                case "admin" -> admin.viewAllHosts();
                case "exit" -> {
                    System.out.println("Exiting the calendar program. Goodbye!");
                    keepRunning = false;
                }
                default -> System.out.println("Invalid role. Please enter 'host', 'admin', or 'exit'.");
            }
        }
    }

    private static void handleHost() {
        System.out.print("Enter your name: ");
        String hostName = scanner.nextLine();
        Host host = new Host(hostName);
        admin.addHost(host);

        while (true) {
            System.out.println("As a host, you can:\n1. Create an event\n2. View all events\n3. Go back to main menu");
            System.out.print("Enter your choice (1, 2, or 3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createEvent(host);
                case 2 -> displayHostEvents(host);
                case 3 -> { System.out.println("Returning to main menu..."); return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void createEvent(Host host) {
        System.out.print("Enter event title: ");
        String title = scanner.nextLine();

        System.out.print("Enter event date (yyyy-mm-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);

        System.out.print("Enter event time (HH:mm): ");
        LocalTime time = LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

        System.out.print("Enter event location: ");
        String location = scanner.nextLine();

        System.out.print("Enter event type (party, conference, concert, misc.): ");
        String type = scanner.nextLine();

        Event event = new Event(title, date, time, location, type);

        while (true) {
            System.out.print("Would you like to add a guest to the event? (yes/no): ");
            String addGuest = scanner.nextLine().trim().toLowerCase();

            if ("yes".equals(addGuest)) {
                addGuestToEvent(event);
            } else if ("no".equals(addGuest)) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
            }
        }

        host.addEvent(event);
        System.out.println("Event created successfully and added to your list!");
    }

    private static void addGuestToEvent(Event event) {
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        System.out.print("Enter guest email: ");
        String email = scanner.nextLine();

        System.out.print("Enter guest role (staff, attendee, custom): ");
        String role = scanner.nextLine();

        Guest guest = new Guest(guestName, email, role);
        event.addGuest(guest);
        System.out.println("Guest added successfully!");

        System.out.print("Would you like to set the RSVP status for this guest? (yes/no): ");
        if ("yes".equals(scanner.nextLine().trim().toLowerCase())) {
            System.out.print("Did this guest RSVP? (yes/no): ");
            guest.setRsvp("yes".equals(scanner.nextLine().trim().toLowerCase()));
            System.out.println("RSVP status updated.");
        }
    }

    private static void displayHostEvents(Host host) {
        if (host.getEvents().isEmpty()) {
            System.out.println("No events scheduled.");
        } else {
            System.out.println("All Scheduled Events (Chronological Order):");
            for (Event event : host.getEvents()) {
                System.out.println(event);
                for (Guest guest : event.getGuests()) {
                    System.out.println("  - " + guest);
                }
            }
        }
    }
}
